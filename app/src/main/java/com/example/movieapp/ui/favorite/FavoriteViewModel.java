package com.example.movieapp.ui.favorite;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieapp.commons.Parser;
import com.example.movieapp.models.Movie;
import com.example.movieapp.models.User;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FavoriteViewModel extends ViewModel {

    static List<Movie> movies = null;
    static User user;
    private MutableLiveData<List<Movie>> lstFav;


    public FavoriteViewModel() {
        lstFav = new MutableLiveData<>();
    }

    public LiveData<List<Movie>> getListFavorites() {
//        String data = "[{\"releaseDate\":\"2019-11-23T11:14:25.276Z\",\"comment\":[\"5dda273dd5119ee8fcc9d3c6\",\"5dda28163948c7f6c5a1c3f1\",\"5dda28533e3badfa964f9cf7\",\"5dda285f3e3badfa964f9cf8\"],\"_id\":\"5dd91491758f543c76ac1c62\",\"title\":\"Biệt đội Titan\",\"description\":\"Biệt Đội Titan do lo sợ một thế lực ma quái đang cố gắng chiếm lấy bản thân mình, nữ phù thủy trẻ tuổi Raven (Teagan Croft) liền tìm gặp chàng thanh tra Dick Grayson Robin (Brenton Thwaites) để cầu xin sự giúp đỡ. \\\\\\\\n Sử dụng năng lực tâm linh, cô nàng đã thuyết phục được Grayson nhờ nói trúng kí ức đau buồn nhất đời anh: mất bố mẹ trong một vụ tai nạn nghề xiếc thương tâm. \\\\\\\\n Ngoài hai nhân vật này, hai thành viên khác là Beast Boy (Ryan Potter), Star Fire “phiên bản gây tranh cãi” (Anna Diop) cùng cặp đôi phản anh hùng Hawk và Dove.\",\"thumbnail\":\"https://film-vietvite.herokuapp.com/images/action/bietdoititan2.jpg\",\"rate\":5,\"viewNumber\":41426,\"movieUrl\":\"https://film-vietvite.herokuapp.com/movies/action/bietdoititan.mp4\",\"__v\":0,\"coverImg\":\"https://film-vietvite.herokuapp.com/images/action/bietdoititan2-cover.jpg\",\"duration\":92,\"quality\":\"Full HD\",\"vote\":1032},{\"releaseDate\":\"2019-11-23T11:17:17.017Z\",\"comment\":[],\"_id\":\"5dd9153d758f543c76ac1c66\",\"title\":\"Kẻ hủy diệt 6\",\"description\":\"Terminator: Dark Fate hứa hẹn là phần phim chắc chắn sẽ khiến các fan hâm mộ thỏa mãn bởi những pha hành động cực kỳ chất lượng. Cùng với đó, sự trở lại của đạo diễn James Cameron, Linda Hamilton và Arnold Schwarzenegge càng đem đến nhiều hơn sự kích thích và phấn khích.\",\"thumbnail\":\"https://film-vietvite.herokuapp.com/images/action/kehuydiet6.jpg\",\"rate\":3.5,\"viewNumber\":41426,\"movieUrl\":\"https://film-vietvite.herokuapp.com/movies/action/kehuydiet6.mp4\",\"__v\":0,\"coverImg\":\"https://film-vietvite.herokuapp.com/images/action/kehuydiet6-cover.jpg\",\"duration\":92,\"quality\":\"Full HD\",\"vote\":1032},{\"releaseDate\":\"2019-11-23T11:18:58.382Z\",\"comment\":[],\"_id\":\"5dd915a2758f543c76ac1c68\",\"title\":\"Thấy\",\"description\":\"Trong tương lai xa, một virus tiêu diệt gần hết loài người. Những người còn sống đều bị mù, Jason Momoa vào vai Baba Voss, cha của một cặp sinh đôi ra đời nhiều thể kỷ sau thảm họa với năng lực thần thoại là nhìn thấy. Baba Voss phải bảo vệ bộ lạc khỏi một nữ hoàng hùng mạnh nhưng tuyệt vọng, bà ta tin năng lực nhìn thấy là phép phù thủy và muốn tiêu diệt họ. Alfre Woodard vào vai Paris, thủ lĩnh tinh thần của Baba Voss.\",\"thumbnail\":\"https://film-vietvite.herokuapp.com/images/action/thay.jpg\",\"rate\":4,\"viewNumber\":34078,\"movieUrl\":\"https://film-vietvite.herokuapp.com/movies/action/thay.mp4\",\"__v\":0,\"coverImg\":\"\",\"duration\":92,\"quality\":\"Full HD\",\"vote\":1032},{\"releaseDate\":\"2019-11-23T11:20:50.303Z\",\"comment\":[],\"_id\":\"5dd91612758f543c76ac1c6a\",\"title\":\"Joker\",\"description\":\"Joker từ lâu đã là siêu ác nhân huyền thoại của điện ảnh thế giới. Nhưng có bao giờ bạn tự hỏi, Joker đến từ đâu và điều gì đã biến Joker trở thành biểu tượng tội lỗi của thành phố Gotham? JOKER sẽ là cái nhìn độc đáo về tên ác nhân khét tiếng của Vũ trụ DC – một câu chuyện gốc thấm nhuần, nhưng tách biệt rõ ràng với những truyền thuyết quen thuộc xoay quanh nhân vật mang đầy tính biểu tượng này. Bộ phim đã xuất sắc giành giải thưởng Sư Tử Vàng- Phim Hay Nhất tại LHP Venice lần thứ 76, cùng tràng pháo tay dài 8 phút, và lời khen ngợi dành cho diễn xuất của tài tử Joaquin Phoenix. Một bộ phim không thể bỏ lỡ của tháng 10 năm nay.\",\"thumbnail\":\"https://film-vietvite.herokuapp.com/images/action/joker.jpg\",\"rate\":4.9,\"viewNumber\":38012,\"movieUrl\":\"https://film-vietvite.herokuapp.com/movies/action/joker.mp4\",\"__v\":0,\"coverImg\":\"https://film-vietvite.herokuapp.com/images/action/joker-cover.jpg\",\"duration\":92,\"quality\":\"Full HD\",\"vote\":1032},{\"releaseDate\":\"2019-12-05T12:56:28.212Z\",\"comment\":[],\"_id\":\"5de8fe9aa50bf0aa9a7ec096\",\"title\":\"Avengers Endgame\",\"description\":\"Biệt Đội Siêu Anh Hùng 4: Tàn Cuộc (Avengers 4: Endgame) ra mắt vào tháng 4/2019 sẽ giải quyết triệt để những khúc mắc đã vạch ra suốt 22 bộ phim trước đó của Vụ trụ điện ảnh Marvel (MCU). Hai tháng sau đó, Người Nhện 2 là khởi đầu hoàn toàn mới dành cho MCU. Trong phần trước, tên Titan điên rồ luôn bị ám ảnh về sứ mệnh phải \\\"cân bằng\\\" vũ trụ. Sau khi thu thập đủ 6 viên đá vô cực, Thanos khiến nửa thế giới tan biến thành cát bụi chỉ bằng một cái búng tay. Mở đầu của teaser trailer Endgame, Tony Stark dùng 1 cái mặt nạ của bộ giáp Iron Man để ghi hình lại tin nhắn video gởi cho người vợ Pepper Potts. Anh cho biết là mình đang trên đường trở về Trái đất nhưng không may bị trôi lạc trong vũ trụ, nước và lương thực thì cạn sạch từ 4 ngày trước, oxy thì qua ngày mai cũng hết, mạng sống bây giờ như chỉ mành treo chuông, thần chết chỉ còn cách vài tiếng. Tiếp theo đó là sự xuất hiện của Black Widow, Captain America, Hulk, Hawkeye, Thor, Nebula... những người trong số 50% dân số vũ trụ còn sống sót sau cái búng tay của Thanos. Và ngay lúc này, Black Widow cùng Cap và Hulk phải lên kế hoạch để giải cứu thế giới, à không, là giải cứu vũ trụ, một lần nữa. Avengers phần 4 sẽ là bộ phim cuối cùng có sự góp mặt của một số siêu anh hùng đời đầu của đội Avengers, điển hình là Steve Rogers/Captain America. Ngoài ra, cuối đoạn teaser còn có sự xuất hiện của Ant Man Scott Lang, có thể là Scott đã thoát được khỏi Lượng tử giới và trở về để cùng mọi người đánh bại Thanos chăng?\",\"thumbnail\":\"https://film-vietvite.herokuapp.com/images/action/avengers4.jpg\",\"rate\":4.9,\"viewNumber\":38012,\"movieUrl\":\"https://film-vietvite.herokuapp.com/movies/action/avengers4.mp4\",\"coverImg\":\"https://film-vietvite.herokuapp.com/images/action/avengers4-cover.jpg\",\"vote\":1032,\"duration\":92,\"quality\":\"Full HD\",\"__v\":0}]";
//        lstFav.setValue(parse(data));
        if(movies == null)
            new MovieService().execute();
        else {
        }

        lstFav.setValue(movies);
        return lstFav;
    }

    class MovieService extends AsyncTask<String, Void, List<Movie>> implements Callback {

        @Override
        protected List<Movie> doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();

            HttpUrl.Builder urlBuilder = HttpUrl.parse("https://film-vietvite.herokuapp.com/api/favorite").newBuilder();
            urlBuilder.addPathSegment("5def5b80c2bd5c8b261e9e8e");
            String url = urlBuilder.build().toString();
            Log.e("url", url);

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            client.newCall(request).enqueue(this);
            return null;
        }

        @Override
        public void onFailure(Call call, IOException e) {
            Log.e("Error", "Network Error");
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            try {
                String resData = response.body().string();
                JSONObject o = new JSONObject(resData);
                String favStr = o.getString("favorite");
                movies = Parser.parseListMovie(favStr);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}