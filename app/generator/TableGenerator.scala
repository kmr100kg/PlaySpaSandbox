package generator

import slick.codegen.SourceCodeGenerator

object TableGenerator {
  def main(args: Array[String]): Unit = {
    val slickDriver = "slick.jdbc.MySQLProfile"
    val jdbcDriver = "com.mysql.cj.jdbc.Driver"
    // FIXME 最新のMySQLDiverを使うといくつかエラーがでるのでパラメータを渡して解決しています
    val url = "jdbc:mysql://localhost:3306/playsample?nullNamePatternMatchesAll=true&characterEncoding=UTF-8&serverTimezone=JST"
    val user = "root"
    val password = "pass"
    val output = "app"
    val pkg = "model"

    // 配列の要素は以下の順序でないと動きません
    val settings = Array(slickDriver, jdbcDriver, url, output, pkg, user, password)

    SourceCodeGenerator.main(settings)
  }
}
