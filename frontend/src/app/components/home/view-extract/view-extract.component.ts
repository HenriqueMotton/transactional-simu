import { Component, Input } from '@angular/core';
import { DividerModule } from 'primeng/divider';
import { Account, Extract } from '../../../domain/customer';

@Component({
  selector: 'app-view-extract',
  standalone: true,
  imports: [
    DividerModule
  ],
  templateUrl: './view-extract.component.html',
  styleUrl: './view-extract.component.scss'
})
export class ViewExtractComponent {
  @Input() dataClientAccount: Account = {};
  @Input() dataExtract: Extract[] = [];

  dataExtractFormated: Extract[] = [];
  
  ngOnChanges(){
    if(this.dataExtract.length > 0){
      this.dataExtract = this.dataExtract.map((extract: Extract) => ({
        ...extract,
        type: extract.type === 'DEBIT' ? 'débito' : 'crédito'
      }));
    }
  }
}
