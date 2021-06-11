package controllers

import javax.inject._
import models.Student
import play.api.libs.json.{Format, Json}
import play.api.mvc._
import utils.DatabaseUtil

import scala.collection.mutable.ListBuffer

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class StudentController @Inject()(cc: ControllerComponents,db: DatabaseUtil) extends AbstractController(cc) {

//  private implicit val studentFormatter = Json.format[Student]
  private implicit val studentFormatter: Format[Student] = Json.format[Student]

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */

  private val studentList = new ListBuffer[Student]()
  studentList += Student(1, "a", "10th",Set("math","hist"),Map("math"->10,"hist"->20))
  studentList += Student(2, "b", "12th",Set("math","hist"),Map("math"->13,"hist"->22))

  def students() = Action { implicit request: Request[AnyContent] =>
    Ok(Json.toJson(studentList)).as("application/json")
  }

  def addStudent() = Action { implicit request =>
    val content = request.body
    val jsonObject = content.asJson

    val studentData: Option[Student] =
      jsonObject.flatMap(
        Json.fromJson[Student](_).asOpt
      )
    studentData match {
      case Some(newItem) =>
        val toBeAdded = Student(newItem.id,newItem.name,newItem.course,newItem.subjects,newItem.marks)
        studentList += toBeAdded
        Created(Json.toJson(toBeAdded))
      case None =>
        BadRequest
    }

    Ok(Json.toJson(studentList)).as("application/json")
  }

  def updateStudent(userId: Int) = Action { implicit request: Request[AnyContent] =>
    val content = request.body
    val jsonObject = content.asJson

    val studentData: Option[Student] =
      jsonObject.flatMap(
        Json.fromJson[Student](_).asOpt
      )

    val foundIndex = studentList.indexWhere(_.id == userId)
    foundIndex match {
      case -1 => Ok("Student with with " + userId + " Id  doesn't exist.")
      case index: Int =>
          studentData match {
            case Some(newItem) =>
              val toBeAdded = Student(newItem.id, newItem.name, newItem.course, newItem.subjects, newItem.marks)
              studentList.update(index, toBeAdded)
              Created(Json.toJson(toBeAdded))
            case None =>
              BadRequest
          }
     }
   }

  def getStudent(userId: Int) = Action { implicit request: Request[AnyContent] =>
//    val foundStudent = studentList.find(_.id == userId)
//    foundStudent match {
//      case Some(item) => Ok(Json.toJson(item)).as("application/json")
//      case None => Ok("Student with this Id doesn't exist.")
//    }

    val foundIndex= studentList.indexWhere(_.id== userId)
    foundIndex match {
      case -1 => Ok("Student with with "+userId+" Id  doesn't exist.")
      case index: Int =>
        println(studentList(index))
        Ok(Json.toJson(studentList(index))).as("application/json")
    }
  }

  def deleteStudent(userId: Int) = Action { implicit request: Request[AnyContent] =>
//    val foundStudent = studentList.find(_.id == userId)
//    val index = studentList.indexOf(foundStudent.get)
//    studentList.remove(index)
//      foundStudent match {
//        case Some(item) =>
//          Ok(Json.toJson(item))
//        case None => Ok("Student with this Id doesn't exist.")
//      }
//    }
    val index= studentList.indexWhere(_.id== userId)
    index match {
      case -1 => Ok("Student with with "+userId+" Id  doesn't exist.")
      case _: Int=>
        studentList.remove(index)
        Ok("Student with "+userId+" Id removed successfully.")
    }
  }

  def getDBStudent(userId: Int) = Action { implicit request: Request[AnyContent] =>
//    db.getDBStudent(userId)
//    Ok("application/json")
    Ok(Json.toJson(db.getDBStudent(userId))).as("application/json")
  }

  def addDBStudent() = Action { implicit request: Request[AnyContent] =>
    val content = request.body
    val jsonObject = content.asJson

    val studentData: Option[Student] =
      jsonObject.flatMap(
        Json.fromJson[Student](_).asOpt
      )
    studentData match {
      case Some(newItem) =>
        val toBeAdded = db.addDBStudent(newItem)
        Created("Json.toJson(toBeAdded)")
      case None =>
        BadRequest
    }
    Ok(Json.toJson(studentList)).as("application/json")
  }
}
