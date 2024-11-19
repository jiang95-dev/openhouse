package com.linkedin.openhouse.spark.sql.catalyst.plans.logical

import org.apache.spark.sql.catalyst.plans.logical.Command

case class SetSharingPolicy(tableName: Seq[String], sharing: String) extends Command {
  override def simpleString(maxFields: Int): String = {
    s"SetSharingPolicy: ${tableName} ${sharing}"
  }
}
