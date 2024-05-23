import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { TableModule } from 'primeng/table';
import { Account, Client, Extract } from '../../domain/customer';
import { requestApi } from '../../utils/globalFunctions';
import { ButtonModule } from 'primeng/button';
import { TooltipModule } from 'primeng/tooltip';
import { DialogModule } from 'primeng/dialog';
import { RegisterClientComponent } from './register-client/register-client.component';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { ViewExtractComponent } from './view-extract/view-extract.component';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    TableModule,
    CommonModule,
    ButtonModule,
    TooltipModule,
    DialogModule,
    RegisterClientComponent,
    ToastModule,
    FormsModule,
    ViewExtractComponent,
    InputTextModule
  ],
  providers: [MessageService],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
  clients!: Client[];
  filteredClients: Client[] = [];
  headersTable = [
    {
      title: 'Nome',
    },
    {
      title: 'Idade',
    },
    {
      title: 'E-mail',
    },
    {
      title: 'Número da conta',
    },
    {
      title: 'Transação'
    },
    {
      title: 'Ações'
    }
  ]
  visibleRegisterClient: boolean = false;
  visibleExtract: boolean = false;
  tituloExtract= '';
  dataClientAccount: Account = {};
  dataExtractClient: Extract[] = [];
  searchValue: string | undefined;

  constructor(private messageService: MessageService) {}

  async ngOnInit() {
    await this.getClients(0);
  }

  showToast(type: string, summary: string, content: string) {
    this.messageService.add({ severity: type, summary: summary, detail: content });
  }

  async getClients(pageValue: number, filtro?: string){
    try{
      const baseUrl = `/clients`;
      // const urlPath = filtro ? `${baseUrl}pesquisar=${filtro}&` : baseUrl;
      // const paramsPage = { size: 10, page: pageValue };
      const getClients = await requestApi("GET", baseUrl);
      
      this.clients = getClients.data;
      this.filteredClients = getClients.data;
    }catch(error){
      throw new Error(`Erro ao obter lista de termos: ${error}`);
    }
  }
  
  statusModalClient(status: boolean) {
    this.visibleRegisterClient = status;
  }
  
  statusModalExtract(status: boolean) {
    this.visibleExtract = status;
  }

  async registerFinish(eventStatus: any){
    this.statusModalClient(eventStatus.status);
    if(eventStatus.result === 'success'){
      this.showToast('success', 'Success', 'Usuário salvo com sucesso.');
      await this.getClients(0);
    }
  }

  async viewExtract(client: any){
    const extractClient: Extract[] = await this.getExtractClient(client.numAccount);
    const accountClient: Account = await this.getBalanceClient(client.numAccount);
    this.tituloExtract = `Extrato da conta ${client.numAccount}`
    if(accountClient){
      this.dataClientAccount = accountClient;
      this.statusModalExtract(true);
    }
    if(extractClient){
      this.dataExtractClient = extractClient;
    }
  }

  async getBalanceClient(numAccount: string){
    const baseUrl = `/accounts/${numAccount}`;
    try{
      const getBalanceClient = await requestApi("GET", baseUrl);
      return getBalanceClient.data;
    }catch(error){
      console.log('Erro ao buscar a conta do cliente.');
    }
  }

  async getExtractClient(numAccount: string){
    const baseUrl = `/extracts/${numAccount}`;
    try{
      const getBalanceClient = await requestApi("GET", baseUrl);
      return getBalanceClient.data;
    }catch(error){
      return [];
    }
  }

  filterData(event: Event) {
    const searchText = (event.target as HTMLInputElement).value.toLowerCase();
    this.filteredClients = this.clients.filter(client =>
      client.name?.toLowerCase().includes(searchText) ||
      client.email?.toLowerCase().includes(searchText)
    );
  }

  onlyNumber(event: any) {
    const inputChar = String.fromCharCode(event.keyCode);
    if (!/^\d+$/.test(inputChar)) {
      event.preventDefault();
    }
  }

  async debitValueAccount(valueTransactional: number, numAccount: string, type: string){
    const baseUrl = `/accounts/${numAccount}`;
    const body = {
      value: valueTransactional,
      type
    }
    let wordType = type === 'DEBIT' ? 'debitado' : 'creditado';
    try{
      await requestApi("PUT", baseUrl, body);
      this.showToast('success', 'Success', `${valueTransactional} reais ${wordType} com sucesso!`);
    }catch(error){
      console.log('Erro ao atualizar saldo do cliente.');
    }
  }
}
