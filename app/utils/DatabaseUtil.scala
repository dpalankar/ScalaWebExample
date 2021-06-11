package utils

import com.datastax.driver.core.Row
import javax.inject.Singleton
import models.Student

import scala.jdk.CollectionConverters._
import scala.collection.mutable._

@Singleton
class DatabaseUtil {
  private val session = CassandraClient.session
  private val table = "student"
  private val keysapace = "studentmgmt"

  private val insertPS = session.prepare(s"INSERT INTO $keysapace.$table(id , name, course, subjects, marks) values(?, ?, ?, ?, ?)")

  private val selectPS = session.prepare(s"select * from $keysapace.$table where id=?")

  def addDBStudent(student:Student): Unit = {
    var row = session.execute(insertPS.bind(student.id , student.name, student.course, student.subjects.asJava, student.marks.asJava)).wasApplied()
  }

  def getDBStudent(id: Int): Student = {
    val rows = session.execute(selectPS.bind(id));
    extractStudent(rows.one);
  }

  private def extractStudent(row: Row): Student = {
    println("getStudent Method called START")
    val id = row.getInt("id")
    val name = row.getString("name")
    val course = row.getString("course")
    val subjects = row.getSet("subjects",classOf[String]).asScala
    val marksDb =  row.getMap[String,Integer]("marks",classOf[String],classOf[Integer]).asScala
    val marks:Map[String,Int] = Map.empty;
    marksDb.foreach( map => marks.addOne(map._1 ,map._2))
//    for((k, v) <- marksDb){marks.addOne(k,v)}
    val student = Student(id, name,course,subjects.toSet,marks.toMap)
    println(student)
    println("Hello World")
    student
  }

}
