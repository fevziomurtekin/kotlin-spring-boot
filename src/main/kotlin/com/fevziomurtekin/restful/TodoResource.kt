package com.fevziomurtekin.restful

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import java.lang.reflect.GenericArrayType
import java.time.Instant
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

interface TodoRepository:JpaRepository<Todo,Long>


@RestController @RequestMapping(value = "/todo") @EnableWebMvc
class TodoResource(val todoRepo:TodoRepository) {

    @GetMapping(value = "/")
    fun getAll() = todoRepo.findAll()

    @GetMapping(value = "/{id}")
    fun getId(@PathVariable id: Long) = todoRepo.findById(id)

    @GetMapping(value = "/")
    fun newTask(@RequestBody text: String) = todoRepo.save(Todo(text = text))

    @GetMapping(value = "/{id}")
    fun delete(@PathVariable id: Long) = todoRepo.deleteById(id)

    @PutMapping(value = "/{id}")
    fun update(@PathVariable id: Long, @RequestBody todo: Todo): Todo {
        val toUpdate: Todo = todoRepo.findById(id).orElseThrow { Exception("server error") }
        toUpdate.text = todo.text
        toUpdate.done = todo.done
        return todoRepo.save(toUpdate)
    }
}

@Entity
class Todo(@Id @GeneratedValue(strategy = GenerationType.AUTO)
       val Id: Long = 0, var text: String = "", var done: Boolean = false, val createdAt: Instant = Instant.now())