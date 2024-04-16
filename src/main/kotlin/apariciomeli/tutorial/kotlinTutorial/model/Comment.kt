package apariciomeli.tutorial.kotlinTutorial.model

import jakarta.persistence.*

@Entity
data class Comment (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Int,
    @ManyToOne
    val user: EndUser,
    @ManyToOne
    val module: Module,
    @Column(columnDefinition = "varchar(1000)")
    val commentData: String
){}