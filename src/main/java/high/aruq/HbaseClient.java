
package high.aruq;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.io.compress.Compression;
import org.apache.hadoop.hbase.util.Bytes;


/** 
 * ClassName:HbaseClient <br/> 
 * Function: TODO (hbase的增删改查api简单的使用). <br/> 
 * Reason:   TODO (). <br/> 
 * Date:     2016年12月13日 下午2:35:58 <br/> 
 * @author   yxd 
 * @version   
 * @see       
 */
public class HbaseClient {
	

	/**
	 * 
	 * getTable:(). <br/> 
	 * TODO(根据名称获取数据表).<br/> 
	 * 
	 * @author yxd 
	 * @param tableName
	 * @return
	 */
	public static HTable getTable(String tableName){
		Configuration conf = HBaseConfiguration.create();
		
		HTable htable = null;
		try {
			htable = new HTable(conf, tableName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return htable;
	}
	
	/**
	 * 
	 * scanTable:(). <br/> 
	 * TODO(获取表数据).<br/> 
	 * 
	 * @author yxd 
	 * @param tableName
	 */
	public static void scanTable(String tableName){
		HTable htable = getTable(tableName);
		if(htable !=null){
			Scan scan = new Scan();
			ResultScanner rs = null;
			try {
				rs = htable.getScanner(scan);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(rs!=null){
				for (Iterator iterator = rs.iterator(); iterator
						.hasNext();) {
					Result result = (Result) iterator.next();
					for (int i = 0; i < result.rawCells().length; i++) {
						Cell cell = result.rawCells()[i];
						System.out.println(
								Bytes.toString(CellUtil.cloneRow(cell))
								+"------"+
								Bytes.toString(CellUtil.cloneFamily(cell))
								+"------"+
								Bytes.toString(CellUtil.cloneQualifier(cell))
								+"------"+
								Bytes.toString(CellUtil.cloneValue(cell))
								);
					}
				}
			}
		}
	}
	
	
	/**
	 * 
	 * add:(). <br/> 
	 * TODO().<br/> 
	 * 
	 * @author yxd 
	 * @param tableName 表名
	 * @param rowKey 键
	 * @param faiC 列簇
	 * @param ce 列明
	 * @param value 值
	 */
	public static void add(String tableName,String rowKey,String faiC,String ce ,String value){
		HTable htable =getTable(tableName);
		if(htable!=null){
			Put put = new Put(Bytes.toBytes(rowKey));
			put.add(Bytes.toBytes(faiC), Bytes.toBytes(ce), Bytes.toBytes(value));
			try {
				htable.put(put);
			} catch (RetriesExhaustedWithDetailsException e) {
				e.printStackTrace();
			} catch (InterruptedIOException e) {
				e.printStackTrace();
			}
		}

	} 

	/**
	 * 
	 * delete:(). <br/> 
	 * TODO().<br/> 
	 * 
	 * @author yxd 
	 * @param tableName 表名
	 * @param rowKey 键
	 * @param faiC 列簇
	 * @param ce 列明

	 */
	public static void delete(String tableName,String rowKey,String faiC,String ce){
		HTable htable =getTable(tableName);
		if(htable!=null){
			Delete delete = new Delete(Bytes.toBytes(rowKey));
			delete.deleteColumn(Bytes.toBytes(faiC), Bytes.toBytes(ce));
				try {
					htable.delete(delete);
				} catch (IOException e) {
					e.printStackTrace();
				}
			
		}
	}
	
	public static void conitionScan(String tableName,String perfixStgr){
		HTable htable = getTable(tableName);
		if(htable !=null){
			Scan scan = new Scan();
			Filter filter = new PrefixFilter(Bytes.toBytes(perfixStgr));
			scan.setFilter(filter);
			ResultScanner rs = null;
			try {
				rs = htable.getScanner(scan);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(rs!=null){
				for (Iterator iterator = rs.iterator(); iterator
						.hasNext();) {
					Result result = (Result) iterator.next();
					for (int i = 0; i < result.rawCells().length; i++) {
						Cell cell = result.rawCells()[i];
						System.out.println(
								Bytes.toString(CellUtil.cloneRow(cell))
								+"------"+
								Bytes.toString(CellUtil.cloneFamily(cell))
								+"------"+
								Bytes.toString(CellUtil.cloneQualifier(cell))
								+"------"+
								Bytes.toString(CellUtil.cloneValue(cell))
								);
					}
				}
			}
		}
	}



	/**
	 * 初始化命名空间
	 */
	public void initNameSpace(String nameSpace,String creator){
		HBaseAdmin admin = null;
		try {
			admin = new HBaseAdmin(conf);

			admin.deleteNamespace(nameSpace); //删掉再删除

			NamespaceDescriptor descriptor = NamespaceDescriptor.create(nameSpace)
					.addConfiguration("creator", creator)
					.addConfiguration("createTime", System.currentTimeMillis()+"").build();
			admin.createNamespace(descriptor);
		} catch (MasterNotRunningException e) {
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(admin!=null)
				try {
					admin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}



	/**
	 * 初始化表
	 */
	public void initTable(byte[] tableName,String cfName){
		HBaseAdmin admin = null;
		try {
			admin = new HBaseAdmin(conf);

			if (admin.tableExists(tableName)) {
				admin.disableTable(tableName);
				admin.deleteTable(tableName);
				System.out.println(tableName + "表已经存在,并删除表");
			}
			HTableDescriptor desc = new HTableDescriptor(TableName.valueOf(tableName));
			HColumnDescriptor family = new HColumnDescriptor(Bytes.toBytes(cfName));
			// 开启列簇 -- store的块缓存
			family.setBlockCacheEnabled(true);
			family.setBlocksize(1024*1024*2);

			family.setCompressionType(Compression.Algorithm.SNAPPY);

			family.setMaxVersions(1);
			family.setMinVersions(1);

			desc.addFamily(family);

			//admin.createTable(desc);
			byte[][] splitKeys = {
					Bytes.toBytes("100"),
					Bytes.toBytes("200"),
					Bytes.toBytes("300")
			};
			admin.createTable(desc,splitKeys);





		} catch (MasterNotRunningException e) {
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(admin!=null)
				try {
					admin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}


	public  static  final Configuration conf = HBaseConfiguration.create();

	private static final String TABLE_NAME = "student:stu_info";
	public static void main(String[] args) {
		/**
		 * add
		 */
		String rowKey = "1002";
		String faiC = "info";
		String ce = "info:name";
		String value = "yangxiaodong";

		//add(TABLE_NAME, rowKey, faiC, ce, value);
		
		/**
		 * delete
		 */
		//delete(TABLE_NAME, rowKey, faiC, ce);
		/**
		 * 获取数据
		 */
		//scanTable(TABLE_NAME);
		/**
		 * 高级查询
		 */
		String perfixStr="1002";
		conitionScan(TABLE_NAME, perfixStr);
	}
	
}
  