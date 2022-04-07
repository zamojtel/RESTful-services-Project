import {Component, OnInit} from '@angular/core';
import {HomeExchangeDialogComponent} from "../../pages/home/home-exchange-dialog/home-exchange-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ExchangeService} from "../../pages/home/services/exchange.service";

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {

  constructor(public dialog: MatDialog,
              private _snackBar: MatSnackBar,
              private exchangeService: ExchangeService) {
  }

  ngOnInit(): void {
  }

  onClickTestData(): void {
    this.exchangeService.getTestData().subscribe({
      next: (result) => {
        let dialogRef = this.dialog.open(HomeExchangeDialogComponent, {
          width: '500px',
          data: result
        });
        dialogRef.afterClosed().subscribe({
          next: (event) => {
            console.log('dialog closed');
          }
        })
      },
      error: (error) => {
        this.showError(error);
      }
    })
  }

  onClickError(): void {
    this.exchangeService.getErrorMessage().subscribe({
      next: (result) => {
        console.log("nothing")
      },
      error: (error) => {
        console.log(error);
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
