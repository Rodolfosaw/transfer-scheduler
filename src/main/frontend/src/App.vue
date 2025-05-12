<template>
  <div class="container">
    <h1>Agendar Transferência</h1>
    <form @submit.prevent="submit">
      <div>
        <label>Conta Origem:</label>
        <input v-model="form.originAccount" required />
      </div>
      <div>
        <label>Conta Destino:</label>
        <input v-model="form.destinationAccount" required />
      </div>
      <div>
        <label>Valor:</label>
        <input type="number" v-model.number="form.amount" required />
      </div>
      <div>
        <label>Data da Transferência:</label>
        <input type="date" v-model="form.transferDate" required />
      </div>
      <button type="submit">Agendar</button>
    </form>

    <h2>Extrato de Agendamentos</h2>
    <table border="1">
      <thead>
        <tr>
          <th>ID</th><th>Origem</th><th>Destino</th><th>Valor</th><th>Taxa</th>
          <th>Agendamento</th><th>Transferência</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="t in transfers" :key="t.id">
          <td>{{ t.id }}</td>
          <td>{{ t.originAccount }}</td>
          <td>{{ t.destinationAccount }}</td>
          <td>{{ formatCurrency(t.amount) }}</td>
          <td>{{ formatCurrency(t.fee) }}</td>
          <td>{{ t.schedulingDate }}</td>
          <td>{{ t.transferDate }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import { fetchTransfers, createTransfer } from './services/api';

export default {
  setup() {
    const transfers = ref([]);
    const form = ref({
      originAccount: '',
      destinationAccount: '',
      amount: null,
      transferDate: ''
    });

    const load = async () => {
      const res = await fetchTransfers();
      transfers.value = res.data;
    };

    const submit = async () => {
      try {
        await createTransfer({ ...form.value });
        await load();
        Object.assign(form.value, { originAccount:'', destinationAccount:'', amount:null, transferDate:'' });
      } catch (e) {
        alert(e.response?.data?.message || 'Erro ao agendar');
      }
    };

    const formatCurrency = val =>
      new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(val);

    onMounted(load);

    return { transfers, form, submit, formatCurrency };
  }
};
</script>

<style>
.container { max-width: 600px; margin: 2rem auto; font-family: Arial; }
form div { margin-bottom: 0.5rem; }
button { padding: 0.5rem 1rem; }
table { width: 100%; margin-top: 1rem; border-collapse: collapse; }
th, td { padding: 0.5rem; text-align: left; }
</style>
