package edu.cs4730.progject4;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import edu.cs4730.progject4.db.mydb;


public class catFrag extends Fragment {

    public static final String PROVIDER="edu.cs4730.prog4db";

    public static Uri CONTENT_URI_cat = Uri.parse("content://"+PROVIDER+"/Category");

    public static final String KEY_NAME = "Name";
    public static final String KEY_CATNAME = "CatName";
    public static final String KEY_DATE = "Date";
    public static final String KEY_TYPE = "CheckNum";
    public static final String KEY_AMOUNT = "Amount";
    public static final String KEY_CAT = "Category";
    public static final String KEY_ROWID = "_id";
    public static final String TABLE_CHECKING = "checking";

    private long idlv = -1;
    public long i = -1;
    Context myContext;
    String TAG = "cat frag";
    mydb db;

    Cursor cursor;
    private SimpleCursorAdapter dataAdapter;
    ListView list;

    private OnFragmentCatInteractionListener mListener;

    public catFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_cat,container,false);

        db = new mydb(myContext);
        db.open();

        Uri CONTENT_URI_cat = Uri.parse("content://"+PROVIDER+"/Category");
        //setup the information we want for the contentprovider.
        String[] projection_cat = new String[]{KEY_ROWID, KEY_CATNAME};

        cursor = getActivity().getContentResolver().query(CONTENT_URI_cat, projection_cat, null, null, null);

        if (cursor == null) {
            Log.e(TAG, "cursor is null...");
        }
        Log.i(TAG, "setup up listview");
        list = (ListView) myView.findViewById(R.id.catLV);
        list.setClickable(true);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                idlv = id;
                showPopupMenu(v);
            }
        });

        drawList();
        //set click listener

        return myView;
    }

    private void showPopupMenu(View v) {
        PopupMenu popupM = new PopupMenu(getActivity(), v); //note "this" is the activity context, if you are using this in a fragment.  using getActivity()
        popupM.inflate(R.menu.popup);
        popupM.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.editItem) {
                    i = idlv;
                    mListener.onFragmentCatInteraction(i);


                } else if (id == R.id.deleteItem) {
                    String[] projection_cat = new String[]{KEY_ROWID, KEY_CATNAME};
                    ContentResolver cr = getActivity().getContentResolver();
                    cr.delete(Uri.parse("content://edu.cs4730.prog4db/Accounts/transactions/1/" + idlv), null, null);
                    cursor = getActivity().getContentResolver().query(CONTENT_URI_cat, projection_cat, null, null, null);

                    drawList();


                } else if (id == R.id.newItem) {
                    i = -1;
                    mListener.onFragmentCatInteraction(i);


                }

                return true;
            }
        });
        popupM.show();
    }

        // TODO: Rename method, update argument and hook method into UI event
    public void drawList(){
        // The desired columns to be bound
        String[] columns = new String[]{
                KEY_ROWID, KEY_CATNAME};

        // the XML defined views which the data will be bound to
        int[] to = new int[]{
                R.id.catRID,
                R.id.catName
        };


        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(getActivity(),
                R.layout.cat_lv,
                cursor,
                columns,
                to,
                0);


        // Assign adapter to ListView
        list.setAdapter(dataAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myContext = context;
        Log.d(TAG, "onAttach");
        try {
            mListener = (catFrag.OnFragmentCatInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentCatInteractionListener {
        // TODO: Update argument type and name
        void onFragmentCatInteraction(long i);
    }
}
