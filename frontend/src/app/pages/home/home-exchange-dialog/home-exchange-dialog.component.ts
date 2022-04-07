import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {RatesView} from "../interfaces/rates-view";

@Component({
  selector: 'app-home-exchange-dialog',
  templateUrl: './home-exchange-dialog.component.html',
  styleUrls: ['./home-exchange-dialog.component.css']
})
export class HomeExchangeDialogComponent {

  private readonly _data: RatesView;
  displayedColumns: string[] = ['position', 'name', 'price'];
  tableDataSource: DataSourceTable[] = [];

  get data(): RatesView {
    return this._data;
  }

  constructor(public dialogRef: MatDialogRef<HomeExchangeDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public responseData: RatesView) {
    this._data = responseData;
    let i = 1;
    this._data.rates.forEach(rate => {
      this.tableDataSource.push({
        position: i,
        name: rate.name,
        price: rate.value
      });
      i++;
    })
  }

  closeDialog(): void {
    this.dialogRef.close();
  }
}

export interface DataSourceTable {
  position: number;
  name: string;
  price: number;
}
