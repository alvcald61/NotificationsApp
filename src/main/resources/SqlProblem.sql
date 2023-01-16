SELECT Nombre, Apellido 
FROM Clientes
JOIN Ventas ON Clientes.ID = Ventas.Id_cliente
WHERE Ventas.Fecha >= DATEADD(year, -1, GETDATE())
GROUP BY Clientes.ID
HAVING SUM(Ventas.Importe) > 100000;


-- select cl.*
-- from clientes cl
-- INNER JOIN ventas v on v.id_cliente = cl.id
-- where v.fecha > CURRENT_DATE + interval '-12 months'
-- GROUP BY cl.id
-- HAVING sum(v.importe) > 100000;
