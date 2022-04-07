import {Component, OnInit, ViewChild} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {HomeExchangeDialogComponent} from "./home-exchange-dialog/home-exchange-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {ExchangeService} from "./services/exchange.service";
import {MatDatepicker} from "@angular/material/datepicker";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  submitted = false;
  currencies: string[] = [
    'pln',
    'usd',
    'eur'
  ];

  currencyForm = new FormGroup({
    date: new FormControl('')
  });

  @ViewChild('picker')
  picker: MatDatepicker<string> | undefined


  constructor(
    public dialog: MatDialog,
    private _snackBar: MatSnackBar,
    private exchangeService: ExchangeService) {
  }

  ngOnInit(): void {
  }

  sendForm(): void {
    if(this.submitted) {
      this.showError({error: "Nie można ponownie wysłać zapytania!"});
      return;
    }
    this.submitted = true;
    let date = "";
    if (date) {
      date = this.currencyForm.value.date.format("YYYY-MM-DD");
    }
    this.exchangeService.getExchangeRates(date).subscribe({
      next: (result) => {
        let dialogRef = this.dialog.open(HomeExchangeDialogComponent, {
          width: '500px',
          data: result
        });
        dialogRef.afterClosed().subscribe({
          next: (event) => {
            console.log('dialog closed');
            this.submitted = false;
          }
        })
      },
      error: (error) => {
        this.showError(error);
      }
    })
  }

  showError(error: any): void {
    this._snackBar.open(error.error, 'Zamknij', {
      horizontalPosition: 'left',
      verticalPosition: 'bottom',
      duration: 8000,
      panelClass: ['snackbar-error']
    })
  }
}
