/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package org.apache.cassandra.avro;

@SuppressWarnings("all")
public class CfDef extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = org.apache.avro.Schema.parse("{\"type\":\"record\",\"name\":\"CfDef\",\"namespace\":\"org.apache.cassandra.avro\",\"fields\":[{\"name\":\"keyspace\",\"type\":\"string\"},{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"column_type\",\"type\":[\"string\",\"null\"]},{\"name\":\"comparator_type\",\"type\":[\"string\",\"null\"]},{\"name\":\"subcomparator_type\",\"type\":[\"string\",\"null\"]},{\"name\":\"comment\",\"type\":[\"string\",\"null\"]},{\"name\":\"row_cache_size\",\"type\":[\"double\",\"null\"]},{\"name\":\"key_cache_size\",\"type\":[\"double\",\"null\"]},{\"name\":\"read_repair_chance\",\"type\":[\"double\",\"null\"]},{\"name\":\"gc_grace_seconds\",\"type\":[\"int\",\"null\"]},{\"name\":\"default_validation_class\",\"type\":[\"null\",\"string\"],\"default\":null},{\"name\":\"min_compaction_threshold\",\"type\":[\"null\",\"int\"],\"default\":null},{\"name\":\"max_compaction_threshold\",\"type\":[\"null\",\"int\"],\"default\":null},{\"name\":\"row_cache_save_period_in_seconds\",\"type\":[\"int\",\"null\"],\"default\":0},{\"name\":\"key_cache_save_period_in_seconds\",\"type\":[\"int\",\"null\"],\"default\":3600},{\"name\":\"memtable_flush_after_mins\",\"type\":[\"int\",\"null\"],\"default\":60},{\"name\":\"memtable_throughput_in_mb\",\"type\":[\"null\",\"int\"],\"default\":null},{\"name\":\"memtable_operations_in_millions\",\"type\":[\"null\",\"double\"],\"default\":null},{\"name\":\"rowkey_size\",\"type\":[\"null\",\"int\"]},{\"name\":\"columnfamily_size\",\"type\":[\"null\",\"int\"]},{\"name\":\"columnfamily_type\",\"type\":[\"null\",\"string\"]},{\"name\":\"mysql_engine\",\"type\":[\"null\",\"string\"]},{\"name\":\"id\",\"type\":[\"int\",\"null\"]},{\"name\":\"column_metadata\",\"type\":[{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"ColumnDef\",\"fields\":[{\"name\":\"name\",\"type\":\"bytes\"},{\"name\":\"validation_class\",\"type\":\"string\"},{\"name\":\"index_type\",\"type\":[{\"type\":\"enum\",\"name\":\"IndexType\",\"symbols\":[\"KEYS\"],\"aliases\":[\"org.apache.cassandra.config.avro.IndexType\"]},\"null\"]},{\"name\":\"index_name\",\"type\":[\"string\",\"null\"]}],\"aliases\":[\"org.apache.cassandra.config.avro.ColumnDef\"]}},\"null\"]}],\"aliases\":[\"org.apache.cassandra.config.avro.CfDef\"]}");
  public java.lang.CharSequence keyspace;
  public java.lang.CharSequence name;
  public java.lang.CharSequence column_type;
  public java.lang.CharSequence comparator_type;
  public java.lang.CharSequence subcomparator_type;
  public java.lang.CharSequence comment;
  public java.lang.Double row_cache_size;
  public java.lang.Double key_cache_size;
  public java.lang.Double read_repair_chance;
  public java.lang.Integer gc_grace_seconds;
  public java.lang.CharSequence default_validation_class;
  public java.lang.Integer min_compaction_threshold;
  public java.lang.Integer max_compaction_threshold;
  public java.lang.Integer row_cache_save_period_in_seconds;
  public java.lang.Integer key_cache_save_period_in_seconds;
  public java.lang.Integer memtable_flush_after_mins;
  public java.lang.Integer memtable_throughput_in_mb;
  public java.lang.Double memtable_operations_in_millions;
  public java.lang.Integer rowkey_size;
  public java.lang.Integer columnfamily_size;
  public java.lang.CharSequence columnfamily_type;
  public java.lang.CharSequence mysql_engine;
  public java.lang.Integer id;
  public java.util.List<org.apache.cassandra.avro.ColumnDef> column_metadata;
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return keyspace;
    case 1: return name;
    case 2: return column_type;
    case 3: return comparator_type;
    case 4: return subcomparator_type;
    case 5: return comment;
    case 6: return row_cache_size;
    case 7: return key_cache_size;
    case 8: return read_repair_chance;
    case 9: return gc_grace_seconds;
    case 10: return default_validation_class;
    case 11: return min_compaction_threshold;
    case 12: return max_compaction_threshold;
    case 13: return row_cache_save_period_in_seconds;
    case 14: return key_cache_save_period_in_seconds;
    case 15: return memtable_flush_after_mins;
    case 16: return memtable_throughput_in_mb;
    case 17: return memtable_operations_in_millions;
    case 18: return rowkey_size;
    case 19: return columnfamily_size;
    case 20: return columnfamily_type;
    case 21: return mysql_engine;
    case 22: return id;
    case 23: return column_metadata;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: keyspace = (java.lang.CharSequence)value$; break;
    case 1: name = (java.lang.CharSequence)value$; break;
    case 2: column_type = (java.lang.CharSequence)value$; break;
    case 3: comparator_type = (java.lang.CharSequence)value$; break;
    case 4: subcomparator_type = (java.lang.CharSequence)value$; break;
    case 5: comment = (java.lang.CharSequence)value$; break;
    case 6: row_cache_size = (java.lang.Double)value$; break;
    case 7: key_cache_size = (java.lang.Double)value$; break;
    case 8: read_repair_chance = (java.lang.Double)value$; break;
    case 9: gc_grace_seconds = (java.lang.Integer)value$; break;
    case 10: default_validation_class = (java.lang.CharSequence)value$; break;
    case 11: min_compaction_threshold = (java.lang.Integer)value$; break;
    case 12: max_compaction_threshold = (java.lang.Integer)value$; break;
    case 13: row_cache_save_period_in_seconds = (java.lang.Integer)value$; break;
    case 14: key_cache_save_period_in_seconds = (java.lang.Integer)value$; break;
    case 15: memtable_flush_after_mins = (java.lang.Integer)value$; break;
    case 16: memtable_throughput_in_mb = (java.lang.Integer)value$; break;
    case 17: memtable_operations_in_millions = (java.lang.Double)value$; break;
    case 18: rowkey_size = (java.lang.Integer)value$; break;
    case 19: columnfamily_size = (java.lang.Integer)value$; break;
    case 20: columnfamily_type = (java.lang.CharSequence)value$; break;
    case 21: mysql_engine = (java.lang.CharSequence)value$; break;
    case 22: id = (java.lang.Integer)value$; break;
    case 23: column_metadata = (java.util.List<org.apache.cassandra.avro.ColumnDef>)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
}