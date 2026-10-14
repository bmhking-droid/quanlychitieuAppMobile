package com.example.quanlychitieu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;
import java.util.UUID;

public class AddExpenseActivity extends AppCompatActivity {
    private EditText etTitle, etAmount, etNote;
    private Spinner spCategory;
    private DatePicker dpDate;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        TextView tvBackHeader = findViewById(R.id.tvBackHeader);
        if (tvBackHeader != null) {
            tvBackHeader.setOnClickListener(v -> finish());
        }

        etTitle = findViewById(R.id.etTitle);
        etAmount = findViewById(R.id.etAmount);
        etNote = findViewById(R.id.etNote);
        spCategory = findViewById(R.id.spCategory);
        dpDate = findViewById(R.id.dpDate);
        btnSave = findViewById(R.id.btnSave);

        String[] categories = {"Ăn uống", "Đi lại", "Học tập", "Giải trí", "Khác"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        spCategory.setAdapter(adapter);

        btnSave.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String amountStr = etAmount.getText().toString().trim();
            String category = spCategory.getSelectedItem().toString();
            String note = etNote.getText().toString().trim();

            if (title.isEmpty()) {
                etTitle.setError("Không được để trống tên khoản chi");
                return;
            }

            if (amountStr.isEmpty()) {
                etAmount.setError("Không được để trống số tiền");
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(amountStr);
                if (amount <= 0) {
                    etAmount.setError("Số tiền phải lớn hơn 0");
                    return;
                }
            } catch (NumberFormatException e) {
                etAmount.setError("Số tiền không hợp lệ");
                return;
            }

            String date = String.format(Locale.getDefault(), "%02d/%02d/%d", 
                    dpDate.getDayOfMonth(), dpDate.getMonth() + 1, dpDate.getYear());

            Expense newExpense = new Expense(UUID.randomUUID().toString(), title, amount, category, date, note);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("NEW_EXPENSE", newExpense);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}