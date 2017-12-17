package com.example.android.bookstore.CustomerFragments;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bookstore.Common;
import com.example.android.bookstore.Database.Database;
import com.example.android.bookstore.Model.Order;
import com.example.android.bookstore.Model.Request;
import com.example.android.bookstore.R;
import com.example.android.bookstore.ViewHolder.CartAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.hoang8f.widget.FButton;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CartFrahment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CartFrahment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFrahment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    TextView txtTotalPrice;
    FButton btnPlace;

    List<Order> cart = new ArrayList<>();
    CartAdapter adapter;

    public CartFrahment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFrahment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFrahment newInstance(String param1, String param2) {
        CartFrahment fragment = new CartFrahment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_cart_frahment, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = (TextView)view.findViewById(R.id.total);
        btnPlace = (FButton)view.findViewById(R.id.btnPlaceOrder);

        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });

        loadBookList();

        return view;
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("One more step..");
        alertDialog.setMessage("Enter Your Address : ");

        final EditText edtAddress = new EditText(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        edtAddress.setLayoutParams(lp);
        alertDialog.setView(edtAddress);    //Add edit Text to alert dialog
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Create a new Request
                Request request = new Request(
                        Common.currentCustomer.getPhoneNumber(),
                        Common.currentCustomer.getName(),
                        edtAddress.getText().toString(),
                        txtTotalPrice.getText().toString(),
                        "0",
                        cart
                );
                //Submit to Firebase
                //WE will be using System.CureentMilli to key
                requests.child(String.valueOf(System.currentTimeMillis()))
                        .setValue(request);
                //Delete Cart
                new Database(getActivity()).cleanCart();
                Toast.makeText(getActivity(), "Thank You, Order Placed", Toast.LENGTH_SHORT).show();

            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }

    private void loadBookList() {
        cart = new Database(getActivity()).getCarts();
        adapter = new CartAdapter(cart,getActivity());
        recyclerView.setAdapter(adapter);

        //calculate total price
        int total = 0;
        for (Order order:cart)
            total += (Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
        Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        txtTotalPrice.setText(fmt.format(total));
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

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
        void onFragmentInteraction(Uri uri);
    }
}
