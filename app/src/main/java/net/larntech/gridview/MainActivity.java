package net.larntech.gridview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int[] images = {R.drawable.apple,R.drawable.banana, R.drawable.kiwi, R.drawable.oranges, R.drawable.watermelon};
    private String[] names = {"Apple", " Banana", "Kiwi", "Oranges", "Watermelon"};
    private String[] description = {"This is apple"," This is Banana", "This is Kiwi", "This is Oranges", "This is watermelon"};

    private List<ItemsModal> itemsModalList = new ArrayList<>();

  GridView gridView;
  CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //loop through and add to our list
        for(int i=0; i < names.length; i++){

            ItemsModal itemsModal = new ItemsModal(images[i],names[i],description[i]);
            itemsModalList.add(itemsModal);

        }

        gridView = findViewById(R.id.gridView);
        customAdapter = new CustomAdapter(itemsModalList, this);

        gridView.setAdapter(customAdapter);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.searchView);

        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.e("TAG", " new text ==> "+newText);

                customAdapter.getFilter().filter(newText);
                return true;
            }
        });


        return true;
    }

    public class CustomAdapter extends BaseAdapter implements Filterable {

        private List<ItemsModal> itemsModalList;
        private List<ItemsModal> itemsModalListFilter;
        private Context context;

        public CustomAdapter(List<ItemsModal> itemsModalList, Context context) {
            this.itemsModalList = itemsModalList;
            this.itemsModalListFilter = itemsModalList;
            this.context = context;
        }

        @Override
        public int getCount() {
            return itemsModalList.size();
        }

        @Override
        public Object getItem(int i) {
            return itemsModalList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = view;

            final ItemsModal itemsModal = itemsModalList.get(i);

            if(view1 == null) {
                view1 = LayoutInflater.from(context).inflate(R.layout.row_items, viewGroup, false);
            }

            ImageView imageName = view1.findViewById(R.id.imageName);
            TextView tvName = view1.findViewById(R.id.tvName);
            TextView tvDesc = view1.findViewById(R.id.tvDesc);

            String name = itemsModal.getName();
            String desc = itemsModal.getDescription();
            int image = itemsModal.getImage();

            imageName.setImageResource(image);
            tvName.setText(name);
            tvDesc.setText(desc);

            view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("CLICKED ITEM", " ===> "+itemsModal.getName());
                    startActivity(new Intent(MainActivity.this, ClickeditemActivity.class).putExtra("data",itemsModal ));
                }
            });

            return view1;
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    FilterResults filterResults = new FilterResults();
                    if(charSequence == null || charSequence.length() == 0){

                        filterResults.count = itemsModalListFilter.size();
                        filterResults.values = itemsModalListFilter;

                    }else{
                        String searchChr = charSequence.toString().toLowerCase();
                        List<ItemsModal> searchResult = new ArrayList<>();

                        for(ItemsModal itemsModal:itemsModalListFilter){
                            if(itemsModal.getName().toLowerCase().contains(searchChr) || itemsModal.getDescription().toLowerCase().contains(searchChr)){
                                searchResult.add(itemsModal);
                            }
                        }

                        filterResults.count = searchResult.size();
                        filterResults.values = searchResult;

                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                    itemsModalList = (List<ItemsModal>) filterResults.values;
                    notifyDataSetChanged();

                }
            };

            return filter;
        }
    }
}