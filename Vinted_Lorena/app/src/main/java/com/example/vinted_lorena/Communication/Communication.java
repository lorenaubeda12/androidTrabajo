package com.example.vinted_lorena.Communication;

import android.content.Intent;

public interface Communication {
    void showDetails(Intent i);
    void exportInvoice(int idCli, int idOrden, String fileName);
}
