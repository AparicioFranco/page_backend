package apariciomeli.tutorial.kotlinTutorial.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
data class ModuleData (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id:Int,
    @ManyToOne(fetch = FetchType.EAGER)
    val module: Module,
    val title: String,
    @Column(columnDefinition = "varchar(1000)")
    val text: String,
    val video: String,
    val audio: String,
    val link: String,
    var linkText: String,
    val file: String
){

}