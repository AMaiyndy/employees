package ru.technoserv.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.technoserv.domain.AuditInfo
import ru.technoserv.services.AuditService
import java.time.Instant
import java.util.*

@RestController
@RequestMapping(value="audit", produces = arrayOf("application/json"))
open class AuditController (private val service : AuditService){

    @RequestMapping(name="14", value="/getInfoDep/{depId}", method = arrayOf(RequestMethod.POST))
    fun getDepAudit(@PathVariable depId: Int, @RequestBody searchDate: SearchDate) : List<AuditInfo>{
        return service.getRecordsOfPeriodForDepartment(searchDate ,depId)
    }

    @RequestMapping(name="15", value="/getInfoEmp/{empId}", method = arrayOf(RequestMethod.POST))
    fun getEmpAudit(@PathVariable empId: Int, @RequestBody searchDate: SearchDate) : List<AuditInfo>{
        return service.getRecordsOfPeriodForEmployee(searchDate ,empId)
    }

}