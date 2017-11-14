package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

/**
 * Created by chathuranga on 11/14/2017.
 */

/*
 * Copyright 2015 Department of Computer Science and Engineering, University of Moratuwa.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *                  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

import android.content.Context;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.MyApplication;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InMemoryTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;


import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InMemoryTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.MyApplication;
/**
 *
 */
public class PersistentMemoryDemo extends ExpenseManager {


    public PersistentMemoryDemo(Context context) {
        setup(context);
    }


    public void setup(Context context) {
        /*** Begin generating dummy data for In-Memory implementation ***/

        TransactionDAO inMemoryTransactionDAO = new InMemoryTransactionDAO();
        setTransactionsDAO(inMemoryTransactionDAO);

        //AccountDAO inMemoryAccountDAO = new InMemoryAccountDAO();
        AccountDAO persistentAccountDAO = new PersistentAccountDAO(context);
        setAccountsDAO(persistentAccountDAO);

        /*
        //print as message
        Context context = getApplicationContext();
        PersistentAccountDAO obj = new PersistentAccountDAO(context);
        //obj.addAccount(new Account("123456789","peopl","chat",));
        List<String> accList = obj.getAccountNumbersList();
        for (String text:accList) {
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
*/

        // dummy data
       // Account dummyAcct1 = new Account("7878", "Yoda Bank", "Anakin Skywalker", 10000.0);
//        Account dummyAcct2 = new Account("78945Z", "Clone BC", "Obi-Wan Kenobi", 80000.0);
        //getAccountsDAO().addAccount(dummyAcct1);
//        getAccountsDAO().addAccount(dummyAcct2);

        /*** End ***/
    }

    @Override
    public void setup() throws ExpenseManagerException {
        //do nothing here
    }
}


