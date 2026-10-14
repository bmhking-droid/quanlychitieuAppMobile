package com.example.quanlychitieu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView tvTotalAmount;
    private ListView lvExpenses;
    private Button btnToAdd;
    private ArrayList<Expense> expenseList;
    private ExpenseAdapter adapter;

    private final ActivityResultLauncher<Intent> addLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Expense newExpense = (Expense) result.getData().getSerializableExtra("NEW_EXPENSE");
                    if (newExpense != null) {
                        expenseList.add(0, newExpense);
                        updateData();
                    }
                }
            });

    private final ActivityResultLauncher<Intent> detailLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    boolean isDeleted = result.getData().getBooleanExtra("IS_DELETED", false);
                    int position = result.getData().getIntExtra("POSITION", -1);
                    if (isDeleted && position >= 0 && position < expenseList.size()) {
                        expenseList.remove(position);
                        updateData();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        lvExpenses = findViewById(R.id.lvExpenses);
        btnToAdd = findViewById(R.id.btnToAdd);

        expenseList = new ArrayList<>();
        expenseList.add(new Expense("1", "Ăn sáng", 30000, "Ăn uống", "12/10/2026", "Phở bò"));
        expenseList.add(new Expense("2", "Đổ xăng", 50000, "Đi lại", "12/10/2026", "Xăng RON 95"));
        expenseList.add(new Expense("3", "Mua sách", 120000, "Học tập", "13/10/2026", "Sách Lập trình Android"));

        adapter = new ExpenseAdapter(this, expenseList);
        lvExpenses.setAdapter(adapter);

        updateData();

        btnToAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
            addLauncher.launch(intent);
        });

        lvExpenses.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(MainActivity.this, ExpenseDetailActivity.class);
            intent.putExtra("EXPENSE_ITEM", expenseList.get(position));
            intent.putExtra("POSITION", position);
            detailLauncher.launch(intent);
        });
    }

    private void updateData() {
        adapter.notifyDataSetChanged();
        double total = 0;
        for (Expense item : expenseList) {
            total += item.getAmount();
        }
        DecimalFormat formatter = new DecimalFormat("#,###");
        tvTotalAmount.setText(formatter.format(total) + " đ");
    }
}