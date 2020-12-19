package com.example.pasaronline.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.pasaronline.BuildConfig;
import com.example.pasaronline.Payment;
import com.example.pasaronline.R;
import com.example.pasaronline.adapter.KeranjangAdapter;
import com.example.pasaronline.model.Keranjang;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback;
import com.midtrans.sdk.corekit.core.MidtransSDK;
import com.midtrans.sdk.corekit.core.TransactionRequest;
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme;
import com.midtrans.sdk.corekit.models.BankType;
import com.midtrans.sdk.corekit.models.CustomerDetails;
import com.midtrans.sdk.corekit.models.ItemDetails;
import com.midtrans.sdk.corekit.models.snap.CreditCard;
import com.midtrans.sdk.corekit.models.snap.TransactionResult;
import com.midtrans.sdk.uikit.SdkUIFlowBuilder;

import java.util.ArrayList;
import java.util.List;


public class KeranjangFragment extends Fragment implements TransactionFinishedCallback {
    //buat kerjanjang user
    private RecyclerView mRecycle;
    private KeranjangAdapter mAdapter;
    private ProgressBar mProgres;

    private DatabaseReference mDatabaseReff;
    private List<Keranjang> mKeranjang;

    public KeranjangFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        makePayment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_keranjang, container, false);

        mRecycle = view.findViewById(R.id.keranjangRv);
        mRecycle.setHasFixedSize(true);
        mRecycle.setLayoutManager(new LinearLayoutManager(getContext()));

        mProgres = view.findViewById(R.id.progress_circle1);

        mKeranjang = new ArrayList<>();

        mDatabaseReff = FirebaseDatabase.getInstance().getReference("keranjang");
        mDatabaseReff.orderByChild("idPembeli").equalTo(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot  postSnapShot : snapshot.getChildren()){
                    Keranjang keranjang = postSnapShot.getValue(Keranjang.class);
                    mKeranjang.add(keranjang);

                }

                mAdapter = new KeranjangAdapter(getContext(), mKeranjang);
                mRecycle.setAdapter(mAdapter);
                mProgres.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                mProgres.setVisibility(View.INVISIBLE);
            }
        });

//        if (mDatabaseReff.orderByChild("idPembeli").equals(FirebaseAuth.getInstance().getUid())){
//
//        }else {
//            Toast.makeText(getContext(), "Tidak ada data barang", Toast.LENGTH_SHORT).show();
//        }

        Button bayar = view.findViewById(R.id.btn_bayar);
        bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickPay();
            }
        });
        return view;






    }
    public void makePayment() {
        SdkUIFlowBuilder.init()
                .setContext(getContext())
                .setClientKey(BuildConfig.MERCHANT_CLIENT_KEY)
                .setMerchantBaseUrl(BuildConfig.MERCHANT_BASE_URL)
                .enableLog(true)
                .setTransactionFinishedCallback(this)
                .setColorTheme(new CustomColorTheme("#777777", "#f77474", "#3f0d0d"))
                .buildSDK();
    }


    private void clickPay() {
        MidtransSDK.getInstance().setTransactionRequest(transactionRequest("101", 20000, 1, "John"));
        MidtransSDK.getInstance().startPaymentUiFlow(getContext());
    }



    public static CustomerDetails customerDetails() {
        CustomerDetails cd = new CustomerDetails();
        cd.setFirstName("SUGENG");
        cd.setEmail("siswanto@gmail.com");
        cd.setPhone("0822222");
        return cd;
    }

    public static TransactionRequest transactionRequest(String id, int price, int qty, String name) {
        TransactionRequest request = new TransactionRequest(System.currentTimeMillis() + " ", 20000);
        request.setCustomerDetails(customerDetails());
        ItemDetails details = new ItemDetails(id, price, qty, name);

        ArrayList<ItemDetails> itemDetails = new ArrayList<>();
        itemDetails.add(details);
        request.setItemDetails(itemDetails);
        CreditCard creditCard = new CreditCard();
        creditCard.setSaveCard(false);
        creditCard.setAuthentication(CreditCard.AUTHENTICATION_TYPE_RBA);
        creditCard.setBank(BankType.BCA);

        request.setCreditCard(creditCard);
        return request;
    }


    @Override
    public void onTransactionFinished(TransactionResult result) {
        if (result.getResponse() != null) {
            switch (result.getStatus()) {
                case TransactionResult.STATUS_SUCCESS:
                    Toast.makeText(getContext(), "Transaksi Berhasil ID : " + result.getResponse().getTransactionId(), Toast.LENGTH_SHORT).show();
                    break;
                case TransactionResult.STATUS_PENDING:
                    Toast.makeText(getContext(), "Transaksi Pending ID : " + result.getResponse().getTransactionId(), Toast.LENGTH_SHORT).show();
                    break;
                case TransactionResult.STATUS_FAILED:
                    Toast.makeText(getContext(), "Transaksi Gagal ID : " + result.getResponse().getTransactionId(), Toast.LENGTH_SHORT).show();
                    break;
            }
            result.getResponse().getValidationMessages();
        } else if (result.isTransactionCanceled()) {
            Toast.makeText(getContext(), "Transaksi dibatalkan", Toast.LENGTH_LONG).show();
        } else {
            if (result.getStatus().equalsIgnoreCase((TransactionResult.STATUS_INVALID))) {
                Toast.makeText(getContext(), "Transaksi gak valid" + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Ada yang salah nih", Toast.LENGTH_LONG).show();
            }

        }
    }

}