package com.example.quanlychitieu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;

public class ExpenseDetailActivity extends AppCompatActivity {
    private TextView tvDetailTitle, tvDetailAmount, tvDetailCategory, tvDetailDate, tvDetailNote;
    private Button btnBack, btnDelete;
    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_detail);

        TextView tvBackHeader = findViewById(R.id.tvBackHeader);
        if (tvBackHeader != null) {
            tvBackHeader.setOnClickListener(v -> finish());
        }

        tvDetailTitle = findViewById(R.id.tvDetailTitle);
        tvDetailAmount = findViewById(R.id.tvDetailAmount);
        tvDetailCategory = findViewById(R.id.tvDetailCategory);
        tvDetailDate = findViewById(R.id.tvDetailDate);
        tvDetailNote = findViewById(R.id.tvDetailNote);
        btnBack = findViewById(R.id.btnBack);
        btnDelete = findViewById(R.id.btnDelete);

        Expense expense = (Expense) getIntent().getSerializableExtra("EXPENSE_ITEM");
        position = getIntent().getIntExtra("POSITION", -1);

        if (expense != null) {
            tvDetailTitle.setText(expense.getTitle());
            DecimalFormat formatter = new DecimalFormat("#,###");
            tvDetailAmount.setText(formatter.format(expense.getAmount()) + " đ");
            tvDetailCategory.setText(expense.getCategory());
            tvDetailDate.setText(expense.getDate());
            tvDetailNote.setText(expense.getNote() != null && !expense.getNote().isEmpty() ? expense.getNote() : "Không có ghi chú");
        }

        btnBack.setOnClickListener(v -> finish());

        btnDelete.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("IS_DELETED", true);
            resultIntent.putExtra("POSITION", position);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}