package apariciomeli.tutorial.kotlinTutorial.model

import jakarta.persistence.*

@Entity
data class PasswordKey (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Int,
    @OneToOne
    @Column(unique = true)
    val user: EndUser,
    val key: String
){}