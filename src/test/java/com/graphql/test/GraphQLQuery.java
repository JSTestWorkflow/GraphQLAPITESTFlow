package com.graphql.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.io.payload.PayloadAPI;
import com.io.pojo.GraphQLPojo;
import com.io.pojo.QueryPojoVariable;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class GraphQLQuery {

	/**
	 * @Description This method is designed to validate all films title.
	 * @apiNote https://swapi-graphql.netlify.app
	 * @author Jagannath Swain
	 * @Date 14/8/2022
	 */
	@Test
	public void graphQLTest() {
		try {
			RestAssured.baseURI = "https://swapi-graphql.netlify.app";
			String query = "{\"query\":\"{\\n  allFilms {\\n    films {\\n      title\\n    }\\n  }\\n}\\n\",\"variables\":null}";

			given().log().all().contentType("application/json").body(query).when().log().all()
					.post("/.netlify/functions/index").then().log().all().assertThat().statusCode(200).and()
					.body("data.allFilms.films[0].title", equalTo("A New Hope"));
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			System.out.println("Must executable code");
		}
	}

	/**
	 * @Description This method is designed to validate space mission status.
	 * @apiNote https://api.spacex.land/graphql
	 * @author Jagannath Swain
	 * @Date 14/8/2022
	 */
	@Test
	public void spaceDetails() {
		try {
			RestAssured.baseURI = "https://api.spacex.land";
			String query = "{\"query\":\"{\\n  ships {\\n    image\\n    missions {\\n      flight\\n      name\\n    }\\n  }\\n}\\n\",\"variables\":null}";

			given().log().all().contentType("application/json").body(query).when().log().all().post("/graphql").then()
					.log().all().assertThat().statusCode(200);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			System.out.println("Must executable code");
		}
	}

	/**
	 * @Description This method is designed to validate space launch site.
	 * @apiNote https://api.spacex.land/graphql/
	 * @author Jagannath Swain
	 * @Date 14/8/2022
	 */
	@Test
	public void compAndLaunchDetails() {
		try {
			RestAssured.baseURI = "https://api.spacex.land";
			String query = "{\"query\":\"{\\n  company {\\n    ceo\\n    employees\\n  }\\n  launches {\\n    launch_site {\\n      site_id\\n      site_name\\n    }\\n  }\\n}\\n\",\"variables\":null}";

			given().log().all().contentType("application/json").body(query).when().log().all().post("/graphql").then()
					.log().all().assertThat().statusCode(200).and()
					.body("data.launches[0].launch_site.site_id", equalTo("ccafs_slc_40"));
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			System.out.println("Must executable code");
		}
	}

	/**
	 * @Description This method is designed to validate space launch status using
	 *              data provider.
	 * @apiNote https://api.spacex.land/graphql/
	 * @author Jagannath Swain
	 * @Date 14/8/2022
	 */
	@DataProvider
	public Object[][] getQueryData() {
		return new Object[][] { { 10, 2 }, };
	}

	@Test(dataProvider = "getQueryData")
	public void getLaunchesDetailsTest(int offset, int limit) {
		try {
			RestAssured.baseURI = "https://api.spacex.land";
			String query = "{\"query\":\"{\\n  company {\\n    ceo\\n  }\\n  launches(offset: " + offset + ", limit: "
					+ limit + ") {\\n    id\\n    details\\n  }\\n}\\n\",\"variables\":null}";

			given().log().all().contentType("application/json").body(query).when().log().all().post("/graphql").then()
					.log().all().assertThat().statusCode(200);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			System.out.println("Must executable code");
		}
	}

	/**
	 * @Description This testcase is designed to validate company,country and engine
	 *              details using POJO Class.
	 * @apiNote https://api.spacex.land/graphql/
	 * @return Company,country and engine details.
	 * @author Jagannath Swain
	 * @Date 14/8/2022
	 */
	@Test
	public void getSpaceDetailsWithPojoTest() {
		try {
			// Object declaration
			RestAssured.baseURI = "https://api.spacex.land";
			QueryPojoVariable variable;
			int limitVal = 2;
			GraphQLPojo query = new GraphQLPojo();

			// Passing query request
			query.setQuery("query($limit: Int!){\r\n" + "  rockets(limit: $limit) {\r\n" + "  company\r\n"
					+ "  country\r\n" + "  engines {\r\n" + "    engine_loss_max\r\n" + "    layout\r\n" + "    }\r\n"
					+ "  }\r\n" + "}");

			// Passing limit value and verify status
			variable = new QueryPojoVariable();
			variable.setLimit(limitVal);
			query.setVariables(variable);

			given().log().all().contentType(ContentType.JSON).body(query).when().log().all().post("/graphql").then()
					.log().all().assertThat().statusCode(200).and()
					.body("data.rockets[1].country", equalTo("United States"));
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			System.out.println("Must executable code");
		}
	}

	/**
	 * @Description This method is designed to create mutation - Insert
	 * @apiNote https://hasura.io/learn/graphql
	 * @author Jagannath Swain
	 * @Date 14/8/2022
	 */
	@Test
	public void createMutation() {
		try {
			RestAssured.baseURI = "https://hasura.io/learn";
			String mutation = "{\"query\":\"mutation {\\n  insert_todos_one(object: {is_public: true, title: \\\"MyName\\\"}) {\\n    user {\\n      id\\n      name\\n    }\\n  }\\n}\\n\",\"variables\":null}";

			String response = given().log().all().contentType("application/json").header("Authorization",
					"Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ik9FWTJSVGM1UlVOR05qSXhSRUV5TURJNFFUWXdNekZETWtReU1EQXdSVUV4UVVRM05EazFNQSJ9.eyJodHRwczovL2hhc3VyYS5pby9qd3QvY2xhaW1zIjp7IngtaGFzdXJhLWRlZmF1bHQtcm9sZSI6InVzZXIiLCJ4LWhhc3VyYS1hbGxvd2VkLXJvbGVzIjpbInVzZXIiXSwieC1oYXN1cmEtdXNlci1pZCI6ImF1dGgwfDYyZTNiNGRjZTgxMTZlOWZjZTgyNmM4MyJ9LCJuaWNrbmFtZSI6ImphZ2FubmF0aDA2NzU1IiwibmFtZSI6ImphZ2FubmF0aDA2NzU1QGdtYWlsLmNvbSIsInBpY3R1cmUiOiJodHRwczovL3MuZ3JhdmF0YXIuY29tL2F2YXRhci8xN2ViMmM5OWJmYWE3YTIwOWI3NWU0ZTY5MWYzNWEzNj9zPTQ4MCZyPXBnJmQ9aHR0cHMlM0ElMkYlMkZjZG4uYXV0aDAuY29tJTJGYXZhdGFycyUyRmphLnBuZyIsInVwZGF0ZWRfYXQiOiIyMDIyLTA4LTI5VDE1OjEwOjMxLjgzOVoiLCJpc3MiOiJodHRwczovL2dyYXBocWwtdHV0b3JpYWxzLmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHw2MmUzYjRkY2U4MTE2ZTlmY2U4MjZjODMiLCJhdWQiOiJQMzhxbkZvMWxGQVFKcnprdW4tLXdFenFsalZOR2NXVyIsImlhdCI6MTY2MTg4MDY0MiwiZXhwIjoxNjYxOTE2NjQyLCJhdF9oYXNoIjoiT0xuZnFGN3pPR2tybzh6S19TSkw4USIsInNpZCI6Inp0Z2xreTdlQmZoeTFZdEFXc3NCUTJ0RmcxSFYtTzRuIiwibm9uY2UiOiJiTjlNbGZMYWRJTzNJY1RRaUpxNzM2ZDh0dW9YUWZfaCJ9.Y6zDbRwX7YVHtF_HJ_FHM8MA9YMQwIyAdkrU2sr8WdqhHBRZUi4vLSJ87iyQkjKy1GPa1DwWJy9512RQ18JdLWODvod2lsfwN5qsmXYyLZOhcVqohKnPmJugTubuiYBJPVKgOlLXcLvGoO0d7OeIiCa-dz-cYhjz1shmuIkHBdt0V2LLTajWui6atAjhNA4DLfmFvU7srrKkq4sPxwGR8rU6pt4OeLBx6zSszhJXBpGvhIw-b5wzBiILsLn9ssO3Zr-yM1vISshVIpX0xlOiQrG66U_v0PoZ-cVKH7b8XCoQddZQDHYT4i8ylrH6AfVfIf0SuYfgGrykq4GxSnaQGw")
					.body(mutation).when().log().all().post("/graphql").then().log().all().assertThat().statusCode(200)
					.extract().response().asString();
			System.out.println("Inserted data is " + response);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			System.out.println("Must executable code");
		}
	}

	/**
	 * @Description This method is designed to pass the payload from different
	 *              package.
	 * @apiNote https://api.spacex.land/graphql/
	 * @author Jagannath Swain
	 * @Date 14/8/2022
	 */
	@Test
	public void passPayloadFromOutside() {
		try {
			RestAssured.baseURI = "https://api.spacex.land";
			String payload = PayloadAPI.getLaunchSiteAPI();

			String response = given().log().all().contentType("application/json").body(payload).when().log().all()
					.post("/graphql").then().log().all().assertThat().statusCode(200).extract().response().asString();
			System.out.println("Successfully payload passed " + response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("Must executable code");
		}
	}

	/**
	 * @Description This testcase is designed to validate the user details using
	 *              POJO Class.
	 * @apiNote https://hasura.io/learn
	 * @return user and id details
	 * @author Jagannath Swain
	 * @Date 14/8/2022
	 */
	@Test
	public void getUserDetailsWithPojoTest() {
		try {
			// Variable declaration
			RestAssured.baseURI = "https://hasura.io/learn";
			QueryPojoVariable variable;
			int limitVal = 10;
			GraphQLPojo query = new GraphQLPojo();

			// Passing query request
			query.setQuery("query ($limit: Int!){\r\n" + "  users(limit: $limit) {\r\n" + "    id\r\n" + "    name\r\n"
					+ "  }\r\n" + "}");

			// Passing limit value and verify status
			variable = new QueryPojoVariable();
			variable.setLimit(limitVal);
			query.setVariables(variable);


			given().log().all().contentType(ContentType.JSON).header("Authorization",
					"Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ik9FWTJSVGM1UlVOR05qSXhSRUV5TURJNFFUWXdNekZETWtReU1EQXdSVUV4UVVRM05EazFNQSJ9.eyJodHRwczovL2hhc3VyYS5pby9qd3QvY2xhaW1zIjp7IngtaGFzdXJhLWRlZmF1bHQtcm9sZSI6InVzZXIiLCJ4LWhhc3VyYS1hbGxvd2VkLXJvbGVzIjpbInVzZXIiXSwieC1oYXN1cmEtdXNlci1pZCI6ImF1dGgwfDYyZTNiNGRjZTgxMTZlOWZjZTgyNmM4MyJ9LCJuaWNrbmFtZSI6ImphZ2FubmF0aDA2NzU1IiwibmFtZSI6ImphZ2FubmF0aDA2NzU1QGdtYWlsLmNvbSIsInBpY3R1cmUiOiJodHRwczovL3MuZ3JhdmF0YXIuY29tL2F2YXRhci8xN2ViMmM5OWJmYWE3YTIwOWI3NWU0ZTY5MWYzNWEzNj9zPTQ4MCZyPXBnJmQ9aHR0cHMlM0ElMkYlMkZjZG4uYXV0aDAuY29tJTJGYXZhdGFycyUyRmphLnBuZyIsInVwZGF0ZWRfYXQiOiIyMDIyLTA4LTI5VDE1OjEwOjMxLjgzOVoiLCJpc3MiOiJodHRwczovL2dyYXBocWwtdHV0b3JpYWxzLmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHw2MmUzYjRkY2U4MTE2ZTlmY2U4MjZjODMiLCJhdWQiOiJQMzhxbkZvMWxGQVFKcnprdW4tLXdFenFsalZOR2NXVyIsImlhdCI6MTY2MTg4MDY0MiwiZXhwIjoxNjYxOTE2NjQyLCJhdF9oYXNoIjoiT0xuZnFGN3pPR2tybzh6S19TSkw4USIsInNpZCI6Inp0Z2xreTdlQmZoeTFZdEFXc3NCUTJ0RmcxSFYtTzRuIiwibm9uY2UiOiJiTjlNbGZMYWRJTzNJY1RRaUpxNzM2ZDh0dW9YUWZfaCJ9.Y6zDbRwX7YVHtF_HJ_FHM8MA9YMQwIyAdkrU2sr8WdqhHBRZUi4vLSJ87iyQkjKy1GPa1DwWJy9512RQ18JdLWODvod2lsfwN5qsmXYyLZOhcVqohKnPmJugTubuiYBJPVKgOlLXcLvGoO0d7OeIiCa-dz-cYhjz1shmuIkHBdt0V2LLTajWui6atAjhNA4DLfmFvU7srrKkq4sPxwGR8rU6pt4OeLBx6zSszhJXBpGvhIw-b5wzBiILsLn9ssO3Zr-yM1vISshVIpX0xlOiQrG66U_v0PoZ-cVKH7b8XCoQddZQDHYT4i8ylrH6AfVfIf0SuYfgGrykq4GxSnaQGw")
					.body(query).when().log().all().post("/graphql").then().log().all().assertThat().statusCode(200)
					.and().body("data.users[0].name", equalTo("tui.glen"));
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			System.out.println("Must executable code");
		}
	}

}
