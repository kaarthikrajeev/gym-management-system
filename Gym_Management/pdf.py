import pdfkit

# HTML code for the invoice
invoice_html = """
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Invoice</title>
	<style>
		table {
			border-collapse: collapse;
			width: 100%;
		}

		th, td {
			text-align: left;
			padding: 8px;
			border-bottom: 1px solid #ddd;
		}

		th {
			background-color: #f2f2f2;
		}
	</style>
</head>
<body>
	<h1>Invoice</h1>
	<table>
		<tr>
			<th>Index</th>
			<th>Customer</th>
			<th>Pay Amount</th>
			<th>Paid Date</th>
		</tr>
		{% for i in data %}
		<tr>
			<td>{{loop.index}}</td>
			<td>{{i['first_name']}}</td>
			<td>{{i['amount']}}</td>
			<td>{{i['payment_date']}}</td>
		</tr>
		{% endfor %}
	</table>
</body>
</html>
"""

# Data for the invoice
data = [
    {
        'first_name': 'John',
        'amount': 100,
        'payment_date': '2022-01-01'
    },
    {
        'first_name': 'Jane',
        'amount': 200,
        'payment_date': '2022-02-01'
    }
]

# Render the HTML code with the data
rendered_html = invoice_html.format(data=data)

# Convert the HTML code to a PDF file
pdfkit.from_string(rendered_html, 'invoice.pdf')
