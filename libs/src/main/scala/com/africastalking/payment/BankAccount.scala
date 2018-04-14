package com.africastalking.payment

final case class BankAccount(accountName: String, accountNumber: String, bankCode: BankCode.Value, dateOfBirth: String) {
  override def toString: String =
    s"""
       {"accountName": ${this.accountName}, "accountNumber": ${this.accountNumber}, "bankCode": ${this.bankCode}, "dateOfBirth": ${this.dateOfBirth} }
     """
}

object BankCode extends Enumeration {
  val FCMB_NG       = Value(234001)
  val Zenith_NG     = Value(234002)
  val Access_NG     = Value(234003)
  val GTBank_NG     = Value(234004)
  val Ecobank_NG    = Value(234005)
  val Diamond_NG    = Value(234006)
  val Providus_NG   = Value(234007)
  val Unity_NG      = Value(234008)
  val Stanbic_NG    = Value(234009)
  val Sterling_NG   = Value(234010)
  val Parkway_NG    = Value(234011)
  val Afribank_NG   = Value(234012)
  val Enterprise_NG = Value(234013)
  val Fidelity_NG   = Value(234014)
  val Heritage_NG   = Value(234015)
  val Keystone_NG   = Value(234016)
  val Skye_NG       = Value(234017)
  val Stanchart_NG  = Value(234018)
  val Union_NG      = Value(234019)
  val Uba_NG        = Value(234020)
  val Wema_NG       = Value(234021)
  val First_NG      = Value(234022)
  val CBA_KE        = Value(254001)
  val UNKNOWN       = Value(-1)
}