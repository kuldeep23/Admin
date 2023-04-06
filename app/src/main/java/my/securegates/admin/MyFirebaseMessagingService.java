package my.securegates.admin;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.securegates.admin.R;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
    getFirebaseMessage(message.getNotification().getTitle(), message.getNotification().getBody());
    }

    public  void getFirebaseMessage(String tittle, String msg){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"mySecureGatesChannel")
                .setSmallIcon(R.drawable.addcar)
                .setContentText(tittle)
                .setContentText(msg)
                .setAutoCancel(true);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(101,builder.build());

    }
}
