package com.example.pasaronline.read;

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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
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
    private ValueEventListener mDBListener;
    private FirebaseUser fUser;

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
        fUser = FirebaseAuth.getInstance().getCurrentUser();

        mDatabaseReff = FirebaseDatabase.getInstance().getReference("keranjang");
        mDBListener = mDatabaseReff.orderByChild("idPembeli").equalTo(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                mKeranjang.clear();

                for (DataSnapshot postSnapShot : snapshot.getChildren()) {
                    Keranjang keranjang = postSnapShot.getValue(Keranjang.class);
                    keranjang.setKey(postSnapShot.getKey());
                    mKeranjang.add(keranjang);
                }

                mAdapter = new KeranjangAdapter(getContext(), mKeranjang);
                mRecycle.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(new KeranjangAdapter.OnItemClickListener() {
                    @Override
                    public void onDeleteClick(int position) {
                        removeItem(position);
                    }
                });
                mProgres.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                mProgres.setVisibility(View.INVISIBLE);
            }
        });

        Button bayar = view.findViewById(R.id.btn_bayar);
        bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickPay();
            }
        });
        return view;

    }

    public void removeItem(int position) {
        Keranjang selectItem = mKeranjang.get(position);
        final String selectedKey = selectItem.getKey();

        mDatabaseReff.child(selectedKey).removeValue();
        Toast.makeText(getContext(), "Data berhasil dihapus", Toast.LENGTH_SHORT).show();

        mKeranjang.remove(position);
        mAdapter.notifyItemRemoved(position);
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
//        mDatabaseReff.orderByChild("idPembeli").equalTo(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                int totalHarga = 0;
//                int totalBarang = 0;
//                for (DataSnapshot postSnapShot : snapshot.getChildren()) {
//                    Keranjang keranjang = postSnapShot.getValue(Keranjang.class);
//                    totalHarga += Integer.parseInt(keranjang.getHargaBarang());
//                    totalBarang += Integer.parseInt(keranjang.getJumlahBarang());
//                }
//                MidtransSDK.getInstance().setTransactionRequest(transactionRequest("1", totalHarga, totalBarang, "Total"));
//                MidtransSDK.getInstance().startPaymentUiFlow(getContext());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        DocumentReference df = DatabaseReference
        MidtransSDK.getInstance().setTransactionRequest(transactionRequest("1", 20000, 1, "semangka"));
        MidtransSDK.getInstance().startPaymentUiFlow(getContext());
    }


    public static CustomerDetails customerDetails() {
        String nama,emial,telp;
        final CustomerDetails cd = new CustomerDetails();
        DocumentReference akun = FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getUid());
        akun.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    cd.setFirstName(documentSnapshot.getString("Name"));
                    cd.setEmail(documentSnapshot.getString("Email"));
                    cd.setPhone(documentSnapshot.getString("Telp"));
                }
            }
        });

        return cd;
    }

    public static TransactionRequest transactionRequest(String id, int price, int qty, String name) {
        TransactionRequest request = new TransactionRequest(System.currentTimeMillis() + " ", price);
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
                Toast.makeText(getContext(), "Transaksi tidak valid" + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Ada yang salah nih", Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDatabaseReff.removeEventListener(mDBListener);
    }
}