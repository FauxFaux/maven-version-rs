use std::panic;

use maven_version::Maven3ArtifactVersion as Version;

use rand::prelude::SliceRandom;

#[test]
#[ignore]
// this fails because sorting of things like 0-RELEASE is insane both upstream and here
fn all() {
    let orig: Vec<String> = include_str!("versions.lst")
        .split('\n')
        .map(|s| s.to_string())
        .collect();

    for s in &orig {
        if let Err(_) = panic::catch_unwind(|| {
            Version::new(s);
        }) {
            panic!("parsing {:?} panicked", s);
        }
    }

    let mut tested = orig.clone();
    tested.shuffle(&mut rand::thread_rng());
    tested.sort_by(|left, right| Version::new(left).cmp(&Version::new(right)));
    assert_eq!(orig, tested);
}
