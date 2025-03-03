class Animal {

  name: string;
  sound: string;

  constructor(name: string, sound: string) {
    this.name = name;

    this.sound = sound;
  }

  makeSound(): void {
    console.log(`${this.name} 發出聲音: ${this.sound}`);
  }

  move(): void {
    console.log(`${this.name} 移動中`);
  }
}

class Cat extends Animal {
  constructor(name: string) {
    super(name, "喵喵");  // 呼叫父類別的建構子，並設定貓的叫聲
  }

  stretch(): void {
    console.log(`${this.name} 正在伸懶腰!`);
  }
}

class Dog extends Animal {
  constructor(name: string) {
    super(name, "汪汪");  // 呼叫父類別的建構子，並設定貓的叫聲
  }

  stretch(): void {
    console.log(`${this.name} 正在伸懶腰!`);
  }
}

let genericAnimal = new Animal("動物", "咕嚕");
genericAnimal.

makeSound();  // 輸出: 動物 發出聲音: 咕嚕

// 創建一個 Cat 物件
let kitty = new Cat("小花");

kitty.makeSound();  // 輸出: 小花 發出聲音: 喵喵
kitty.stretch();
