package jp.kusunoki.hacku2022_android.data.module

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import jp.kusunoki.hacku2022_android.data.model.Comment
import jp.kusunoki.hacku2022_android.data.model.Reply
import timber.log.Timber
import javax.inject.Inject

class FireStoreService @Inject constructor(): FireStoreInputInterface,
    FireStoreUpvoteInterface, FireStoreAgreeInterface
{
    override fun addComment(
        comment: Comment,
        onSuccessListener: (it: DocumentReference) -> Unit,
        onFailureListener: (it: Exception) -> Unit
    ) {
        Timber.d("addComment Start")
        if (comment.comment.isNotBlank() && comment.videoId.isNotBlank()) {
            Firebase.firestore.collection(CollectionName.Comments.tag).add(comment)
                .addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener)
        } else {
            onFailureListener(Exception("You should write comment"))
        }
        Timber.d("addComment End")
    }

    fun getCollection(): CollectionReference {
        return Firebase.firestore.collection(CollectionName.Comments.tag)
    }

    fun getPostDocument(id: String): DocumentReference {
        return Firebase.firestore.collection(CollectionName.Comments.tag).document(id)
    }

    override fun addReply(
        reply: Reply,
        path: String,
        onSuccessListener: (it: DocumentReference) -> Unit,
        onFailureListener: (it: Exception) -> Unit
    ) {
        if (reply.comment.isNotBlank()) {
            Firebase.firestore.collection(CollectionName.Comments.tag).document(path)
                .collection(CollectionName.Reply.tag).add(reply)
                .addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener)
        } else {
            onFailureListener(Exception("You should write comment"))
        }
    }

    override fun upvote(
        path: String,
        onSuccessListener: () -> Unit,
        onFailureListener: (it: Exception) -> Unit
    ) {
        Firebase.firestore.collection(CollectionName.Comments.tag).document(path)
            .collection(CollectionName.Upvote.tag).document(Firebase.auth.currentUser!!.uid).set(
                mapOf<String, String>()
            ).addOnSuccessListener {
                onSuccessListener()
            }.addOnFailureListener(onFailureListener)
    }

    override fun unUpvote(
        path: String,
        onSuccessListener: () -> Unit,
        onFailureListener: (it: Exception) -> Unit
    ) {
        Firebase.firestore.collection(CollectionName.Comments.tag).document(path)
            .collection(CollectionName.Upvote.tag).document(Firebase.auth.currentUser!!.uid)
            .delete().addOnSuccessListener {
                onSuccessListener()
            }.addOnFailureListener(onFailureListener)
    }

    override fun listenUpvote(
        path: String,
        snapShotListener: (snapshot: QuerySnapshot?, e: FirebaseFirestoreException?) -> Unit
    ) {
        Firebase.firestore.collection(CollectionName.Comments.tag).document(path)
            .collection(CollectionName.Upvote.tag).addSnapshotListener(snapShotListener)
    }

    override fun agree(
        path: String,
        onSuccessListener: () -> Unit,
        onFailureListener: (it: Exception) -> Unit
    ) {
        Firebase.firestore.collection(CollectionName.Comments.tag).document(path)
            .collection(CollectionName.Agree.tag).document(Firebase.auth.currentUser!!.uid).set(
                mapOf<String, String>()
            ).addOnSuccessListener {
                onSuccessListener()
            }.addOnFailureListener(onFailureListener)
    }

    override fun unAgree(
        path: String,
        onSuccessListener: () -> Unit,
        onFailureListener: (it: Exception) -> Unit
    ) {
        Firebase.firestore.collection(CollectionName.Comments.tag).document(path)
            .collection(CollectionName.Agree.tag).document(Firebase.auth.currentUser!!.uid)
            .delete().addOnSuccessListener {
                onSuccessListener()
            }.addOnFailureListener(onFailureListener)
    }

    override fun listenAgree(
        path: String,
        snapShotListener: (snapshot: QuerySnapshot?, e: FirebaseFirestoreException?) -> Unit
    ) {
        Firebase.firestore.collection(CollectionName.Comments.tag).document(path)
            .collection(CollectionName.Agree.tag).addSnapshotListener(snapShotListener)
    }
}

interface FireStoreInputInterface {
    fun addComment(
        comment: Comment,
        onSuccessListener: (it: DocumentReference) -> Unit = {},
        onFailureListener: (it: Exception) -> Unit = {}
    )

    fun addReply(
        reply: Reply,
        path: String,
        onSuccessListener: (it: DocumentReference) -> Unit = {},
        onFailureListener: (it: Exception) -> Unit = {}
    )
}

interface  FireStoreUpvoteInterface {
    fun upvote(
        path: String,
        onSuccessListener: () -> Unit = {},
        onFailureListener: (it: Exception) -> Unit = {}
    )
    fun unUpvote(
        path: String,
        onSuccessListener: () -> Unit = {},
        onFailureListener: (it: Exception) -> Unit = {}
    )
    fun listenUpvote(
        path: String,
        snapShotListener: (snapshot: QuerySnapshot?, e: FirebaseFirestoreException?) -> Unit
    )
}

interface FireStoreAgreeInterface {
    fun agree(
        path: String,
        onSuccessListener: () -> Unit = {},
        onFailureListener: (it: Exception) -> Unit = {}
    )
    fun unAgree(
        path: String,
        onSuccessListener: () -> Unit = {},
        onFailureListener: (it: Exception) -> Unit = {}
    )
    fun listenAgree(
        path: String,
        snapShotListener: (snapshot: QuerySnapshot?, e: FirebaseFirestoreException?) -> Unit
    )
}


@Module
@InstallIn(ViewModelComponent::class)
abstract class AnalyticsModule {
    @Binds
    abstract fun provideFireStoreInput(
        fireStoreService: FireStoreService
    ): FireStoreInputInterface
}

sealed class CollectionName(val tag: String) {
    object Comments: CollectionName("comments")
    object Upvote: CollectionName("upvote")
    object Agree: CollectionName("agree")
    object Reply: CollectionName("reply")
}