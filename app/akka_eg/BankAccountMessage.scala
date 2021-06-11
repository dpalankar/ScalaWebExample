package akka_eg

sealed trait BankAccountMessage

case class Deposit(amount: Int)  extends BankAccountMessage
case class Withdraw(amount: Int) extends BankAccountMessage
case object PrintBalance         extends BankAccountMessage