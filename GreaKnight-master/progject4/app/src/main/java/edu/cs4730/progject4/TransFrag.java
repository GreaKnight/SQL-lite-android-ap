package edu.cs4730.progject4;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import edu.cs4730.progject4.db.mydb;


public class TransFrag extends Fragment {

    public static final String KEY_NAME = "Name";
    public static final String KEY_CATNAME = "CatName";
    public static final String KEY_DATE = "Date";
    public static final String KEY_TYPE = "CheckNum";
    public static final String KEY_AMOUNT = "Amount";
    public static final String KEY_CAT = "Category";
    public static final String KEY_ROWID = "_id";
    public static final String TABLE_CHECKING = "checking";
    public static final Uri  CONTENT_URI_trans1 = Uri.parse("content://edu.cs4730.prog4db/Accounts/transactions/1");
    public static final String[] projection_trans = new String[]{TABLE_CHECKING+"."+KEY_ROWID, KEY_DATE,  KEY_TYPE, KEY_NAME, KEY_AMOUNT, KEY_CAT};


    Context myContext;
    mydb db;
    String TAG = "trans frag";

    Cursor cursor;
    private SimpleCursorAdapter dataAdapter;
    ListView list;
    private long idlv = -1;
    private OnFragmentInteractionListener mListener;
    public long i = -1;

    public TransFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_trans,container,false);

        //setup the information we want for the contentprovider.

        db = new mydb(myContext);
        db.open();


        cursor = getActivity().getContentResolver().query(CONTENT_URI_trans1, projection_trans, null, null, null);


        if (cursor == null) {
            Log.e(TAG, "cursor is null...");
            cursor.close();
        }
        Log.i(TAG, "setup up listview");
        list = (ListView) myView.findViewById(R.id.transLV);
        list.setClickable(true);
        list.setLongClickable(true);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return false;
            }
        });
        drawList();

        //set click listener
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                idlv = id;


                showPopupMenu(v);


            }
        });
        return myView;
    }
    public void drawList(){


        // The desired columns to be bound
        String[] columns = new String[]{
                TABLE_CHECKING+"."+KEY_ROWID, KEY_DATE,  KEY_TYPE, KEY_NAME, KEY_AMOUNT, KEY_CAT
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[]{
                R.id.transID,
                R.id.transDate,
                R.id.transType,
                R.id.transName,
                R.id.transAmount,
                R.id.transCat
        };


        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(getActivity(),
                R.layout.trans_lv,
                cursor,
                columns,
                to,
                0);


        // Assign adapter to ListView
        list.setAdapter(dataAdapter);
    }

    private void showPopupMenu(View v){
        PopupMenu popupM = new PopupMenu(getActivity(), v); //note "this" is the activity context, if you are using this in a fragment.  using getActivity()
        popupM.inflate(R.menu.popup);
        popupM.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.editItem){
                    i = idlv;
                    mListener.onFragmentInteraction(i);



                }
                else if (id == R.id.deleteItem){
                    ContentResolver cr = getActivity().getContentResolver();
                    cr.delete(Uri.parse("content://edu.cs4730.prog4db/Accounts/transactions/1/"+idlv),null,null);
                    cursor = getActivity().getContentResolver().query(CONTENT_URI_trans1, projection_trans, null, null, null);

                    drawList();




                }
                else if (id == R.id.newItem) {
                    i =-1;
                    mListener.onFragmentInteraction(i);



                }

                return true;
            }
        });

        popupM.show();
    }

    public void newFrag(){
        Fragment addFrag = new addUpdate();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.pager_header, addFrag);
        //transaction.addToBackStack(null);
        transaction.commit();
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myContext = context;
        Log.d(TAG, "onAttach");
        try {
            mListener = (OnFragmentInteractionListener) context;
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(long i);
    }
}
