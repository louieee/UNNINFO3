package ng.edu.unn.unninfo.News_UI;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PicassoClient {
    public static void downloadImage(Context c, String IMAGEURL,ImageView img){
        if (IMAGEURL.length() > 0 && IMAGEURL != null ) {
            Picasso.with(c).load(IMAGEURL).into(img);
        }
    }

}
