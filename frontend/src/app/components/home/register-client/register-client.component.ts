import { Component, EventEmitter, Output } from '@angular/core';
import { InputTextModule } from 'primeng/inputtext';
import { FloatLabelModule } from 'primeng/floatlabel';
import { FormGroup, FormsModule, ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { requestApi } from '../../../utils/globalFunctions';
import { ProgressSpinnerModule } from 'primeng/progressspinner';


@Component({
  selector: 'app-register-client',
  standalone: true,
  imports: [
    InputTextModule,
    FloatLabelModule,
    FormsModule,
    ReactiveFormsModule,
    ButtonModule,
    ProgressSpinnerModule,
  ],
  templateUrl: './register-client.component.html',
  styleUrl: './register-client.component.scss'
})
export class RegisterClientComponent {
  @Output() statusModal = new EventEmitter<any>();
  formGroup!: FormGroup;
  formBuilder: FormBuilder = new FormBuilder();

  requestStatus = false;
  
  ngOnInit() {
    this.formGroup = this.formBuilder.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      age: [null, [Validators.required, Validators.min(18), Validators.max(120)]],
      email: ['', [Validators.required, Validators.email]],
      numAccount: ['', [Validators.required, Validators.pattern(/^\d{14}$/)]]
    });
  }

  onKeyPressAge(event: any) {
    const inputChar = String.fromCharCode(event.keyCode);
    if (!/^\d+$/.test(inputChar)) {
      event.preventDefault();
    }
  }

  onKeyPressAccount(event: any) {
    const inputChar = String.fromCharCode(event.keyCode);
    const inputLength = (event.target as HTMLInputElement).value.length;

    if (!/^\d+$/.test(inputChar) || inputLength >= 14) {
      event.preventDefault();
    }
  }

  cancelRegister(){
    this.formGroup.reset();
    this.statusModal.emit({status: false, result: 'cancel'});
  }

  async saveCliente(){
    if (this.formGroup.invalid) {
      this.formGroup.markAllAsTouched();
      return;
    }
    try{
      this.requestStatus = true;
      await requestApi('POST', 'clients', this.formGroup.value);
    }catch(error){
      console.log('Erro ao cadastrar usuário.');
    }finally{
      this.requestStatus = false;
      this.statusModal.emit({status: false, result: 'success'});
    }
  }
}
