package apariciomeli.tutorial.kotlinTutorial.repo

import apariciomeli.tutorial.kotlinTutorial.model.EndUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

interface EndUserRepository: CrudRepository<EndUser, Int>, JpaRepository<EndUser, Int> {
    fun findEndUserByEmailIgnoreCase(email:String): EndUser
}