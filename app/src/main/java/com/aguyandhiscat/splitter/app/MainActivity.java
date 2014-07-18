package com.aguyandhiscat.splitter.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends ActionBarActivity {

    static final int REQUEST_PHOTO_RECEIPT = 1;

    File mReceiptFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        File test = new File(sdcard, "test.txt");
//        try {
//            test.createNewFile();
//        }
//        catch(Exception e) {
//            Log.d("Issue", "number1");
//        }
//        Log.d("noissue", "ha");


        // take a picture with your phone
        // integrate with bitbucket
    }

    public String fromImageToText(Bitmap image) {
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sample);

//        File sdcard = Environment.getExternalStorageDirectory();
//
//        TessBaseAPI baseApi = new TessBaseAPI();
//        baseApi.init(sdcard.getAbsolutePath(), "eng");
//        baseApi.setImage(image);
//        String recognizedText = baseApi.getUTF8Text();
//        baseApi.end();

//        Log.d("App", recognizedText);

//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        byte[] byteArray = stream.toByteArray();

        Bitmap.Config config = Bitmap.Config.ARGB_4444;
        Bitmap newBitmap = image.copy(config, true);
        int[] pixels = new int[image.getHeight()*image.getWidth()];
        image.getPixels(pixels, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
        for (int i=0; i<image.getWidth()*5; i++) {
            pixels[i] = Color.BLUE;
        }
        newBitmap.setPixels(pixels, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());


        return "harp";
    }

    public Bitmap adjustedImage(Bitmap image) {
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        Bitmap newBitmap = image.copy(config, true);

        int[] pixels = new int[image.getHeight()*image.getWidth()];
        image.getPixels(pixels, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
        for (int i=0; i<pixels.length; i++) {
            pixels[i] = Color.BLUE;
        }
        newBitmap.setPixels(pixels, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());

        return newBitmap;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void takePictureOfReceipt(View view) {
//        File path = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        try {
//            mReceiptFile = File.createTempFile("receipt", "jpg", path);
//        } catch(IOException e) {
//            Log.e("AHH", "Could not write temp file for receipt image.");
//        }

        if(true || mReceiptFile != null) {
//            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            // Ensure that there's a camera activity to handle the intent
//            if(takePictureIntent.resolveActivity(getPackageManager()) != null) {
//                // Create the File where the photo should go
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
//                        Uri.fromFile(mReceiptFile));
//                startActivityForResult(takePictureIntent, REQUEST_PHOTO_RECEIPT);
//            } else {
//                Log.e("AHH", "No available photo application.");
//            }

            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, REQUEST_PHOTO_RECEIPT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PHOTO_RECEIPT && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");

//            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
////            bmOptions.inJustDecodeBounds = true;
////            BitmapFactory.decodeFile(mReceiptFile.getAbsolutePath(), bmOptions);
////            int photoW = bmOptions.outWidth;
////            int photoH = bmOptions.outHeight;
////
////            // Determine how much to scale down the image
////            int scaleFactor = 1; // Math.min(photoW, photoH);
//
//            // Decode the image file into a Bitmap sized to fill the View
//            bmOptions.inJustDecodeBounds = false;
//            bmOptions.inSampleSize = 1;
//            bmOptions.inPurgeable = true;
//
//            Bitmap bitmap = BitmapFactory.decodeFile(mReceiptFile.getAbsolutePath(), bmOptions);

            Uri selectedImage = data.getData();

            try {
                BitmapFactory.Options o = new BitmapFactory.Options();
                Bitmap bitmapFull = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

//                String recognizedText = fromImageToText(bitmapFull);
                String recognizedText = "k";
                Bitmap bitmapAdjusted = adjustedImage(bitmapFull);

                int toWidth  = (int) (bitmapAdjusted.getWidth() * 0.5);
                int toHeight = (int) (bitmapAdjusted.getHeight() * 0.5);
                Bitmap bitmapThumb = Bitmap.createScaledBitmap(bitmapAdjusted, toWidth, toHeight, true);

//                BitmapFactory.Options o2 = new BitmapFactory.Options();
//                o2.inSampleSize = 8;
//                Bitmap bitmapThumb = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);

                ((ImageView) findViewById(R.id.receipt_thumbnail)).setImageBitmap(bitmapThumb);

//                String recognizedText = fromImageToText(bitmapFull);

                ((TextView) findViewById(R.id.receipt_text)).setText(recognizedText);

                Pattern p = Pattern.compile("(.*)([0-9]+\\.[0-9]{2})");
                Matcher m = p.matcher(recognizedText);
                while (m.find()) { // find next match
                    String itemName = m.group(1);
                    String price = m.group(2);

                    Log.d("Yea", "Name: "+itemName + " Price: "+price);
                }

//                array matches = [
//                    ["2 Beef Burgr (09.95/ea) ", "19.90"],
//                    ["1 Bud Light ", "3.79"],
//                    ["1 Bud ", "4.50"],
//                    ["Sub-total ", "28.19"],
//                    ["TOTAL ", "30.68"],
//                    ["Balance Due ", "30.69"]
//                ]

            } catch(FileNotFoundException e) {

            }
        }
    }

    static public class Receipt {
        private List<PricedItem> mPayableItems;

        public void calculatePayableItems() {
            Iterator<PricedItem> itr = mPayableItems.iterator();

            while(itr.hasNext()) {
                PricedItem pricedItem = itr.next();
            }
        }

        public void getGreatestPricedItem() {

        }
    }

    static public class PricedItem {
        private String name;
        private String price;


    }

    static public class PayableItem {
        private PricedItem mPricedItem;
    }

}
