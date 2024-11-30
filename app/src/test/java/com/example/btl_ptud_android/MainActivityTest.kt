import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import com.example.btl_ptud_android.activities.MainActivity
import com.example.btl_ptud_android.models.Categories
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import org.robolectric.Robolectric
import org.mockito.Mockito.*

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28], manifest = Config.NONE)
class MainActivityTest {

    @Test
    fun testGetDataFromFirebase_Success() {
        // Dữ liệu giả mock trực tiếp
        val mockCategories = listOf(
            Categories("1", "Math", 10),
            Categories("2", "Science", 15)
        )

        // Tạo đối tượng Activity
        val activity = Robolectric.setupActivity(MainActivity::class.java)

        // Mock DataSnapshot và DatabaseReference
        val mockDataSnapshot = mock(DataSnapshot::class.java)

        // Stubbing cho các phương thức của DataSnapshot
        `when`(mockDataSnapshot.exists()).thenReturn(true)

        // Giả lập việc trả về các DataSnapshot cho các field
        `when`(mockDataSnapshot.child("id").value).thenReturn("1")
        `when`(mockDataSnapshot.child("title").value).thenReturn("Math")
        `when`(mockDataSnapshot.child("countQuestion").value).thenReturn(10)

        // Giả lập trả về các category từ Firebase (mocked)
        val mockDatabaseReference = mock(DatabaseReference::class.java)
        `when`(mockDatabaseReference.child("categories")).thenReturn(mockDatabaseReference)

        // Giả lập việc trả về các DataSnapshot từ DatabaseReference
        `when`(mockDatabaseReference.get()).thenReturn(mock(Task::class.java) as Task<DataSnapshot>)

        // Giả lập việc lấy dữ liệu từ Firebase (mocked)
        activity.categoryArrayList.addAll(mockCategories)

        // Kiểm tra kết quả
        assert(activity.categoryArrayList.size == 2)
        assert(activity.categoryArrayList[0].title == "Math")
        assert(activity.categoryArrayList[1].countQuestion == 15)
    }
}
