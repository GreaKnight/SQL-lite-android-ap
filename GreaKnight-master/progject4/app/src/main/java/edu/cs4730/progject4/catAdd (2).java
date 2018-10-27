package edu.cs4730.progject4;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class catAdd extends Fragment {
    public static final String PROVIDER="edu.cs4730.prog4db";

    public static Uri CONTENT_URI_cat = Uri.parse("content://"+PROVIDER+"/Category");
    public static Uri CONTENT_URI_acc = Uri.parse("content://"+PROVIDER+"/Accounts");
    public static Uri CONTENT_URI_trans1 = Uri.parse("content://"+PROVIDER+"/Accounts/transactions/1");

    public static final String KEY_NAME = "Name";
    public static final String KEY_CATNAME = "CatName";
    public static final String KEY_DATE = "Date";
    public static final String KEY_TYPE = "CheckNum";
    public static final String KEY_AMOUNT = "Amount";
    public static final String KEY_CAT = "Category";
    public static final String KEY_ROWID = "_id";
    public static final String TABLE_CHECKING = "checking";
    public long check = 0;
    public String x = "";
    ContentValues values;
    EditText nameTv;
    TextView idTv;

    Button addB;

    private addUpdate.OnFragmentInteractionListener mListener;

    public catAdd() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_cat_add,container,false);


        idTv = (TextView) myView.findViewById(R.id.idTv);
        nameTv = (EditText) myView.findViewById(R.id.name);

        MainActivity activity = (MainActivity) getActivity();
        check = activity.getPassVall();
        x = "" + check;

        idTv.setText(x);



        addB = (Button) myView.findViewById(R.id.updateB);
        addB.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String[] cv_cat = new String[]{KEY_CATNAME};
                                        if(check < 1) {
                                            values = createData(cv_cat, new String[]{nameTv.getText().toString()});
                                            getActivity().getContentResolver().insert(CONTENT_URI_cat, values);
                                        }
                                        else if (check > 0){
                                            values = createData(cv_cat,new String[]{nameTv.getText().toString()});
                                            getActivity().getContentResolver().update(Uri.parse(CONTENT_URI_cat+ "/" + check), values,null,null);
                                        }

                                    }});
        return myView;
    }
    public ContentValues createData(String[] key, String[] data) {
        ContentValues cv = new ContentValues();
        for (int i =0; i<key.length; i++) {
            cv.put(key[i], data[i]);
        }
        return cv;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

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
        void onFragmentInteraction(Uri uri);
    }
}
