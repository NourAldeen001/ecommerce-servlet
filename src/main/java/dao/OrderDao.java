package dao;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import entities.Customer;
import entities.Order;
import entities.OrderStatus;
import exceptions.DataAccessException;
import exceptions.DuplicateKeyException;

public final class OrderDao{
	
	private final Connection connection;
	
	public OrderDao(Connection connection) {
		this.connection = connection;
	}

	
	protected Order mapToOrder(ResultSet rs) throws SQLException {
		long id = rs.getInt("id");
		long customerId = rs.getInt("customer_id");
		LocalDateTime date = rs.getObject("order_date", LocalDateTime.class);
		OrderStatus status = OrderStatus.valueOf(rs.getString("status"));
		BigDecimal totalAmount = rs.getBigDecimal("total_amount");
		
		String customerName = rs.getString("customer_name");
		String customerEmail = rs.getString("customer_email");
		String customerPhone = rs.getString("customer_phone");
		String customerAddress = rs.getString("customer_address");

		Customer customer = new Customer(customerName, customerEmail,
				customerPhone, customerAddress, null);
		customer.setId(customerId);
		Order order = new Order(customer, date, totalAmount, status);
		order.setId(id);
		
		return order;
	}

	
	public Optional<Order> findById(long id) {
		String query = """
						with OrderTotals as (
							 select order_id,
							 sum(price) as total_amount
							 from order_items
							 group by order_id
							)
						select
						 o.id,
					     ot.total_amount,
						 o.order_date,
						 o.status,
						 o.customer_id,
						 c.user_id, 
						 c.name as customer_name,
						 c.email as customer_email,
						 c.phone as customer_phone,
						 c.address as customer_address
						from orders o
						join OrderTotals ot on o.id = ot.order_id
						join customers c on c.id = o.customer_id
						where o.id=?
						""";
		
		try(PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, id);
			try(ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return Optional.of(mapToOrder(resultSet));
				} else {
					return Optional.empty();
				}
			}
			
		}
		catch (SQLException ex) {
			throw new DataAccessException("Database operation failed when execute findById", ex);
		}
	}
	
	

	
	public List<Order> findByStatus(OrderStatus status) {
		String query = """
						with OrderTotals as (
							 select order_id,
							 sum(price) as total_amount
							 from order_items
							 group by order_id
							)
						select
						 o.id,
					     ot.total_amount,
						 o.order_date,
						 o.status,
						 o.customer_id,
						 c.user_id, 
						 c.name as customer_name,
						 c.email as customer_email,
						 c.phone as customer_phone,
						 c.address as customer_address
						from orders o
						join OrderTotals ot on o.id = ot.order_id
						join customers c on c.id = o.customer_id
						where status=?
						""";
		
		try(PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, status.toString());
			try(ResultSet resultSet = statement.executeQuery()) {
				return mapToList(resultSet);
			}
			
		}
		catch (SQLException ex) {
			throw new DataAccessException("Database operation failed when execute findByUsername", ex);
		}
	}
	
	
	
	public void updateStatus(long id, OrderStatus status) {
		String query = "UPDATE orders SET status=? WHERE id=?";
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setString(1, status.toString());
			ps.setLong(2, id);
			ps.executeUpdate();
		} catch (SQLException ex) {
			throw new DataAccessException("Database operation failed when execute updateStatus", ex);
		};
	}
	
	public Order save(Order order) {
		String query = "INSERT INTO orders (customer_id, order_date, status) values (?, ?, ?)";
		try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			ps.setLong(1, order.getCustomer().getId());
			ps.setObject(2, order.getOrderDate());
			ps.setString(3, order.getStatus().toString());
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected > 0) {
				try (ResultSet rs = ps.getGeneratedKeys()) {
					long id = rs.getLong(1);
					order.setId(id);
				}
			}
			return order;
		} catch(SQLSyntaxErrorException ex) {
			throw new DataAccessException("Invalid SQL syntax", ex);
		} catch (SQLIntegrityConstraintViolationException ex) {
			throw new DataAccessException("Invalid order status, date, or customer id", ex);
		} catch (SQLException ex) {
			throw new DataAccessException("Database operation failed when execute insertOrder", ex);
		}
	}

	public void update(Order order) {
		String query = "UPDATE orders SET customer_id = ?, order_date = ?, status = ? WHERE id=?";
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setLong(1, order.getCustomer().getId());
			ps.setObject(2, order.getOrderDate());
			ps.setString(3, order.getStatus().toString());
			ps.setLong(4, order.getId());
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected != 1) {
				throw new SQLException("Could not update order with this id");
			}
		} catch (SQLException ex) {
			throw new DataAccessException("Database operation failed when execute update", ex);
		}
	}

	private List<Order> mapToList(ResultSet rs) throws SQLException {
		List<Order> orders = new ArrayList<>();
		while(rs.next()) {
			orders.add(mapToOrder(rs));
		}
		return orders;
	}


	public List<Order> findAll() {
		String query = """
						with OrderTotals as (
							 select order_id,
							 sum(price) as total_amount
							 from order_items
							 group by order_id
							)
						select
						 o.id,
					     ot.total_amount,
						 o.order_date,
						 o.status,
						 o.customer_id,
						 c.user_id, 
						 c.name as customer_name,
						 c.email as customer_email,
						 c.phone as customer_phone,
						 c.address as customer_address
						from orders o
						join OrderTotals ot on o.id = ot.order_id
						join customers c on c.id = o.customer_id
						""";
		
		try(PreparedStatement statement = connection.prepareStatement(query)) {
			try(ResultSet resultSet = statement.executeQuery()) {
				return mapToList(resultSet);
			}
			
		}
		catch (SQLException ex) {
			throw new DataAccessException("Database operation failed when execute findAll", ex);
		}
	}
	
	
}
