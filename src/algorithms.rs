pub fn distance(matrix: Vec<Vec<f32>>, city1: City, city2: City) -> f32{
    if ((matrix[city1.city_number])[city2.city_number] = -1.0)
    maximum_distance * natural_distance(matrix, city1, city2)
    else
    (matrix[city1.city_number])[city2.city_number]



}

fn maximum_distance(matrix: Vec<Vec<f32>>) -> f32{
    let counter = -1.0;
    for x in 0..matrix.len()
    for y in 0..matrix.len()
    if (counter < (matrix[x])[y])
    counter = (matrix[x])[y])
    counter
}

fn natural_distance(matrix: Vec<Vec<f32>>, city1: City, city2: City) -> f32{
    let r = 6373000.0;

    r * 2 * atan(sqrt(a(city1, city2)),sqrt(1 − a(city1, city2)));
}

fn a(city1: City, city2: City){
    sin(powi((city1.x - city2.x)/2 , 2)) + cos(city1.x) * cos(city2.x) *
    sin(powi((city1.y - city2.y)/2 , 2)) * 1.2
}
// fn convert_radians(g: f32) -> f32{
//     g * f32::consts::PI / 180
// }

// Path: 1,2,3,4,5,6,7,163,164,165,168,172,327,329,331,332,333,489,490,491,492,493,496,653,654,656,657,661,815,816,817,820,823,871,978,979,980,981,982,984
// Evaluation: 3305585.454990047
//
// • Y otra de 150:
//
// Path: 1,2,3,4,5,6,7,8,9,11,12,14,16,17,19,20,22,23,25,26,27,74,75,151,163,164,165,166,167,168,169,171,172,173,174,176,179,181,182,183,184,185,186,187,297,326,327,328,329,330,331,332,333,334,336,339,340,343,344,345,346,347,349,350,351,352,353,444,483,489,490,491,492,493,494,495,496,499,500,501,502,504,505,507,508,509,510,511,512,520,652,653,654,655,656,657,658,660,661,662,663,665,666,667,668,670,671,673,674,675,676,678,815,816,817,818,819,820,821,822,823,825,826,828,829,832,837,839,840,871,978,979,980,981,982,984,985,986,988,990,991,995,999,1001,1003,1004,1037,1038,1073,1075
// Evaluation: 6152051.625245280
