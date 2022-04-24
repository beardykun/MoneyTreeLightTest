package com.example.moneytreelighttest.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.moneytreelighttest.BaseFragment
import com.example.moneytreelighttest.R
import com.example.moneytreelighttest.Utils
import com.example.moneytreelighttest.model.Account
import com.example.moneytreelighttest.model.Transaction
import com.example.moneytreelighttest.ui.theme.MoneyTreeLightTestTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TransactionsFragment : BaseFragment() {

    private val args: TransactionsFragmentArgs by navArgs()
    private val viewModel: TransactionsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return contentView {
            // TODO make a common composable and reuse it for similar type items (Text etc.)
            MoneyTreeLightTestTheme {
                hideProgressView()
                TransactionsScreen(args.account, viewModel, Modifier)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // requesting transactions from json
        viewModel.getTransactionsForAccount(args.account)
    }

    @Composable
    fun TransactionsScreen(account: Account, viewModel: TransactionsViewModel, modifier: Modifier) {
        Scaffold(
            topBar = { TopBar() }
        ) {
            Column {
                AccountInfo(account, modifier)
                TransactionsList(
                    account,
                    viewModel,
                    modifier
                )
            }
        }
    }

    // setting TopBar
    @Composable
    fun TopBar() {
        TopAppBar(
            elevation = 0.dp, title = {
                Text(stringResource(R.string.transaction_screen), color = Color.White)
            },
            navigationIcon = {
                IconButton(onClick = { findNavController().popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back),
                        tint = Color.White
                    )
                }
            }, backgroundColor = colorResource(R.color.teal_700)
        )
    }

    // displaying institution and account currentBalance
    @Composable
    fun AccountInfo(account: Account, modifier: Modifier) {
        Column(
            modifier = modifier.fillMaxWidth().background(colorResource(R.color.teal_700))
                .padding(16.dp)
        ) {
            account.institution?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.h6,
                    color = Color.White
                )
            }
            Text(
                text = Utils.getFormattedSum(account),
                style = MaterialTheme.typography.h5,
                color = Color.White
            )
        }
    }

    // Compose analog or recycler View. we don't need to create Adapter, compose will do it for us
    @Composable
    fun TransactionsList(account: Account, viewModel: TransactionsViewModel, modifier: Modifier) {
        // Remember our own LazyListState to control and observe scrolling
        val listState = rememberLazyListState()
        // Starting to observe list for any changes and recreate it if have any
        val listTransitions = remember { viewModel.mTransactions }
        LazyColumn(state = listState) {
            // populating list with the items
            items(listTransitions) {
                TransactionsListItem(account, it, viewModel, modifier)
            }
        }
    }

    //list item displaying date, description and amount of transaction
    @Composable
    fun TransactionsListItem(
        account: Account,
        transaction: Transaction,
        viewModel: TransactionsViewModel,
        modifier: Modifier
    ) {
        Column {
            //add header item if needed
            if (transaction.hasHeader) {
                MonthInfoHeader(account, transaction, viewModel, modifier)
            }
            Row(
                modifier = modifier.fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            ) {

                Text(
                    text = viewModel.getDayOfMonth(transaction),
                    modifier = modifier.weight(0.15f),
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = transaction.description,
                    modifier = modifier.weight(0.55f),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = Utils.getFormattedSumForTransaction(account, transaction.amount),
                    modifier = modifier.weight(0.3f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1
                )
            }
            Spacer(
                modifier = modifier.fillMaxWidth().height(1.dp).padding(start = 16.dp)
                    .background(colorResource(R.color.teal_200))
            )
        }
    }

    // header displaying the month, the year and balance of this month
    @Composable
    fun MonthInfoHeader(
        account: Account,
        transaction: Transaction,
        viewModel: TransactionsViewModel,
        modifier: Modifier
    ) {
        Row(
            modifier = modifier.fillMaxWidth().background(colorResource(R.color.teal_200))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val balanceChange = remember { mutableStateOf(0.0) }

            Text(
                text = viewModel.getMonthAndYear(transaction, balanceChange),
                modifier = modifier.padding(end = 16.dp),
                style = MaterialTheme.typography.h5
            )
            //checking if the month balance is positive or not and setting corresponding Icon and content description
            val isPositive = balanceChange.value > 0
            val icon =
                if (isPositive) Icons.Default.TrendingUp else Icons.Default.TrendingDown
            val iconDescription =
                if (isPositive) stringResource(R.string.positive_balance) else stringResource(R.string.negative_balance)
            Icon(
                icon,
                contentDescription = iconDescription,
                tint = Color.Black,
                modifier = modifier.padding(end = 16.dp)
            )
            Text(
                text = Utils.getFormattedSumForTransaction(account, balanceChange.value),
                style = MaterialTheme.typography.body1
            )
        }
    }

    @Preview
    @Composable
    fun TransactionsPreview() {
        MoneyTreeLightTestTheme {
            TransactionsScreen(args.account, viewModel, Modifier)
        }
    }
}