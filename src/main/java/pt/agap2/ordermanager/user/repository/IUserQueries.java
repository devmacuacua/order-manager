package pt.agap2.ordermanager.user.repository;

public interface IUserQueries {
	public static final String GET_USER_ENTITY_ORDER_BY_U_ID_DESC = "SELECT u FROM UserEntity u ORDER BY u.id DESC";
}
