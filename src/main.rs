mod cities;
mod world;
mod connections;

fn main() {
    let city_list: Vec<cities::City> = world::initialize();
    let _connections: Vec<Vec<f32>> = connections::initialize(&city_list.len());

}

// fn test_cities(city_list: Vec<cities::City>){
//     println!("City Number {:?}", city_list[517].city_number);
//     println!("City{:?}", city_list[517].city);
//     println!("Country {:?}", city_list[517].country);
//     println!("Population {:?}", city_list[517].population);
//     println!("x {:?}", city_list[517].x);
//     println!("y {:?}", city_list[517].y);
// }
