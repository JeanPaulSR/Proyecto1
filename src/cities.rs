//Estructura que guarda la informacion de las ciudades

pub struct City{
    pub city_number: i32,
    pub city: String,
    pub country: String,
    pub population: i32,
    pub x: f32,
    pub y: f32,
}

impl City {
    // Construct City
    pub fn new(city_number: &i32, city: &str, country: &str, population: &i32, x: &f32, y: &f32) -> City {
        City {
            city_number: *city_number,
            city: city.to_string(),
            country: country.to_string(),
            population: *population,
            x: *x,
            y: *y,
        }
    }

    // pub fn get_city_number(self) -> i32{
    //     self.city_number
    // }
    //
    // pub fn get_city(self) -> String{
    //     self.city
    // }
    //
    // pub fn get_country(self) -> String{
    //     self.country
    // }
    //
    // pub fn get_population(self) -> i32{
    //     self.population
    // }
    //
    // pub fn get_x(self) -> f32{
    //     self.x
    // }
    //
    // pub fn get_y(self) -> f32{
    //     self.y
    // }

}

#[cfg(test)]
mod tests{
    use super::*;
    #[test]
    fn test_city_number(){
        let c = City::new(&1,"Tokyo","Japan",&31480498,&35.685000000000002273,&139.75100000000000477);
        assert_eq!(c.city_number, 1);
    }

    #[test]
    fn test_city(){
        let c = City::new(&1,"Tokyo","Japan",&31480498,&35.685000000000002273,&139.75100000000000477);
        assert_eq!(c.city, "Tokyo");
    }

    #[test]
    fn test_country(){
        let c = City::new(&1,"Tokyo","Japan",&31480498,&35.685000000000002273,&139.75100000000000477);
        assert_eq!(c.country, "Japan");
    }

    #[test]
    fn test_population(){
        let c = City::new(&1,"Tokyo","Japan",&31480498,&35.685000000000002273,&139.75100000000000477);
        assert_eq!(c.population, 31480498);
    }

    #[test]
    fn test_x(){
        let c = City::new(&1,"Tokyo","Japan",&31480498,&35.685000000000002273,&139.75100000000000477);
        assert_eq!(c.x, 35.685000000000002273);
    }

    #[test]
    fn test_y(){
        let c = City::new(&1,"Tokyo","Japan",&31480498,&35.685000000000002273,&139.75100000000000477);
        assert_eq!(c.y, 139.75100000000000477);
    }

}
