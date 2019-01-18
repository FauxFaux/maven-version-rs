extern crate rand;
extern crate maven_version;

use maven_version::Maven3ArtifactVersion as Version;

use rand::prelude::SliceRandom;

#[test]
fn all() {
    let orig: Vec<String> = include_str!("versions.lst")
        .split('\n')
        .map(|s| s.to_string())
        .collect();

    let mut tested = orig.clone();
    tested.shuffle(&mut rand::thread_rng());
    tested.sort_by(|left, right| Version::new(left).cmp(&Version::new(right)));
    assert_eq!(orig, tested);
}
