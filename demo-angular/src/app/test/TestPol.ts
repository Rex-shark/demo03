export class Animal {
  name:string;
  constructor(thenName:string) {
    this.name = thenName;
  }
  move(d:number){
    console.log(this.name +" Animal移動了 :",d);
  }
}

class Snake extends Animal {
  constructor(name:string) {
    super(name);
  }

  override move(d:number = 5){
    console.log("Snake ...");
    super.move(d);
  }
}

class Horse extends Animal {
  constructor(name:string) {
    super(name);
  }

  override move(d:number = 45){
    console.log("Horse ...");
    super.move(d);
  }
}

let sam = new Snake("sam Snake");
let tom:Animal = new Horse("tom Horse");

sam.move();
tom.move(5);
