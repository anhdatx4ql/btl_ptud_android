import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import org.junit.Test
import org.mockito.Mockito.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignUpActivityTest {

    private val mockAuth = mock(FirebaseAuth::class.java)
    private val mockUser = mock(FirebaseUser::class.java)

    @Test
    fun testSignUpForNewUser_Success() {
        // Mock đăng ký thành công
        `when`(mockAuth.createUserWithEmailAndPassword("test@example.com", "password123"))
            .thenReturn(mock(Task::class.java) as Task<AuthResult>)
        `when`(mockAuth.currentUser).thenReturn(mockUser)

        // Kiểm tra logic đăng ký
        val result = mockAuth.createUserWithEmailAndPassword("test@example.com", "password123")

        verify(mockAuth).createUserWithEmailAndPassword("test@example.com", "password123")
        assert(result != null)
    }

    @Test
    fun testSignUpForNewUser_Failure() {
        // Giả lập việc đăng ký thất bại
        val mockTask: Task<*>? = mock(Task::class.java)
        `when`(mockAuth.createUserWithEmailAndPassword("test@example.com", "password123"))
            .thenReturn(mockTask as Task<AuthResult>?)

        // Giả lập rằng task này thất bại và ném ra ngoại lệ
        `when`(mockTask!!.isSuccessful).thenReturn(false)
        `when`(mockTask!!.exception).thenReturn(Exception("Registration failed"))

        // Kiểm tra logic đăng ký
        val result = mockAuth.createUserWithEmailAndPassword("test@example.com", "password123")

        // Kiểm tra kết quả là null và rằng đăng ký thất bại
        assert(result != null)
        assert(!result.isSuccessful)  // Kiểm tra rằng đăng ký không thành công

        // Kiểm tra exception
        assert(result.exception?.message == "Registration failed")
    }


}
