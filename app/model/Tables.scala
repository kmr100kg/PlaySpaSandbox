package model
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.MySQLProfile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Employee.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Employee
   *  @param employeeNumber Database column employee_number SqlType(INT), PrimaryKey
   *  @param name Database column name SqlType(VARCHAR), Length(255,true)
   *  @param kana Database column kana SqlType(VARCHAR), Length(255,true)
   *  @param mailAddress Database column mail_address SqlType(VARCHAR), Length(255,true)
   *  @param password Database column password SqlType(VARCHAR), Length(255,true) */
  case class EmployeeRow(employeeNumber: Int, name: String, kana: String, mailAddress: String, password: String)
  /** GetResult implicit for fetching EmployeeRow objects using plain SQL queries */
  implicit def GetResultEmployeeRow(implicit e0: GR[Int], e1: GR[String]): GR[EmployeeRow] = GR{
    prs => import prs._
    EmployeeRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<[String]))
  }
  /** Table description of table employee. Objects of this class serve as prototypes for rows in queries. */
  class Employee(_tableTag: Tag) extends profile.api.Table[EmployeeRow](_tableTag, Some("playsample"), "employee") {
    def * = (employeeNumber, name, kana, mailAddress, password) <> (EmployeeRow.tupled, EmployeeRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(employeeNumber), Rep.Some(name), Rep.Some(kana), Rep.Some(mailAddress), Rep.Some(password)).shaped.<>({r=>import r._; _1.map(_=> EmployeeRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column employee_number SqlType(INT), PrimaryKey */
    val employeeNumber: Rep[Int] = column[Int]("employee_number", O.PrimaryKey)
    /** Database column name SqlType(VARCHAR), Length(255,true) */
    val name: Rep[String] = column[String]("name", O.Length(255,varying=true))
    /** Database column kana SqlType(VARCHAR), Length(255,true) */
    val kana: Rep[String] = column[String]("kana", O.Length(255,varying=true))
    /** Database column mail_address SqlType(VARCHAR), Length(255,true) */
    val mailAddress: Rep[String] = column[String]("mail_address", O.Length(255,varying=true))
    /** Database column password SqlType(VARCHAR), Length(255,true) */
    val password: Rep[String] = column[String]("password", O.Length(255,varying=true))
  }
  /** Collection-like TableQuery object for table Employee */
  lazy val Employee = new TableQuery(tag => new Employee(tag))
}
